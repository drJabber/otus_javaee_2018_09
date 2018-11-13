package rnk.l10.tags;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StatsTagHandler extends TagSupport {

    private Integer getPrevStatsValue(HttpResponse resp) throws IOException {
        Integer result=null;
        HttpEntity entity=resp.getEntity();
        if (entity!=null){
            try(InputStream stream=entity.getContent()){
                JsonParser parser=new JsonParser();
                JsonObject object=(JsonObject) parser.parse(new JsonReader(new InputStreamReader(stream, "UTF-8")));
                result = object.get("stats_id").getAsInt();
            }
        }

        return result;
    }

    @Override
    public int doEndTag() throws JspException {
        try{

            String statsToken=this.pageContext.getServletContext().getInitParameter("stats-token");
            if ((statsToken==null)||(statsToken.isEmpty())){
                throw new Exception("Необходимо определить маркер статистики");
            }
            String stats_value="{\"test\":\"value\"}";

            HttpServletRequest rq=(HttpServletRequest) pageContext.getRequest();
            HttpPost post=new HttpPost(rq.getContextPath()+"/stats");

            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair("app_token",statsToken));
            params.add(new BasicNameValuePair("stats",stats_value));

            EntityBuilder builder=EntityBuilder.create();
            builder.setContentEncoding("UTF-8");
            builder.setParameters(params);

            post.setEntity(builder.build());

            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResp=httpClient.execute(post);

            HttpServletResponse rsp=(HttpServletResponse)pageContext.getResponse();

            int status=httpResp.getStatusLine().getStatusCode();
            if (status== HttpStatus.SC_OK){
                rsp.addCookie(new Cookie("rnk-stats-tracker",getPrevStatsValue(httpResp).toString()));
            }else{
                rsp.addCookie(new Cookie("rnk-stats-tracker","-1"));
            }

            return SKIP_BODY;

        }catch(Exception ex){
            throw new JspException(ex);
        }
    }
}

package rnk.l10.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsProcessor {

    private PageContext ctx;

    public StatsProcessor(PageContext ctx){
        this.ctx=ctx;
    }


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

    public Integer store_statsx() throws ServletException, IOException{

        String stats_token=ctx.getServletContext().getInitParameter("stats-token");
        if ((stats_token==null)||(stats_token.isEmpty())){
            throw new ServletException("Необходимо определить маркер статистики");
        }

        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        HttpPost post=new HttpPost(rq.getContextPath()+"/stats");

        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("app_token",stats_token));
        params.add(new BasicNameValuePair("stats",get_stats()));

        EntityBuilder builder=EntityBuilder.create();
        builder.setContentEncoding("UTF-8");
        builder.setParameters(params);

        post.setEntity(builder.build());

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResp=httpClient.execute(post);

        int status=httpResp.getStatusLine().getStatusCode();
        if (status== HttpStatus.SC_OK){
            return getPrevStatsValue(httpResp);
        }else{
            return -1;
        }
    }

    public Integer store_stats() throws ServletException, IOException{

        String stats_token=ctx.getServletContext().getInitParameter("stats-token");
        if ((stats_token==null)||(stats_token.isEmpty())){
            throw new ServletException("Необходимо определить маркер статистики");
        }

        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        HttpPost post=new HttpPost(rq.getContextPath()+"/stats");

        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("app_token",stats_token));
        params.add(new BasicNameValuePair("stats",get_stats()));

        EntityBuilder builder=EntityBuilder.create();
        builder.setContentEncoding("UTF-8");
        builder.setParameters(params);

        post.setEntity(builder.build());

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResp=httpClient.execute(post);

        int status=httpResp.getStatusLine().getStatusCode();
        if (status== HttpStatus.SC_OK){
            return getPrevStatsValue(httpResp);
        }else{
            return -1;
        }
    }

    private String get_stats() {
        List<NameValuePair> result=new ArrayList<>();
        result.add(new BasicNameValuePair("test","value"));
        return result.toString();
    }
}

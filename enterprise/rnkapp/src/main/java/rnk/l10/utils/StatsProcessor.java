package rnk.l10.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import rnk.l10.entities.beans.StaffSearchBean;
import rnk.l10.utils.StatsUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
                InputStreamReader sr=new InputStreamReader(stream);
                if (sr!=null){
                    JsonReader jr=new JsonReader(sr);
                    if (jr!=null){
                        JsonParser parser=new JsonParser();
                        JsonElement e=parser.parse(jr);
                        if (!e.isJsonNull()){
                            JsonObject object=(JsonObject) e;
                            result = object.get("stats_id").getAsInt();
                        }
                    }
                }
            }
        }

        return result;
    }

    public Integer store_statsx() throws ServletException, IOException{
        ServletContext sc=ctx.getServletContext();
        String stats_token=sc.getInitParameter("stats-token");
        if ((stats_token==null)||(stats_token.isEmpty())){
            throw new ServletException("Необходимо определить маркер статистики");
        }

        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        HttpPost post=new HttpPost(UrlUtils.getLocalUrl(rq)+sc.getContextPath()+"/stats");

        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("app_token",stats_token));
        params.add(new BasicNameValuePair("stats",get_stats()));

        EntityBuilder builder=EntityBuilder.create();
        builder.setContentType(ContentType.APPLICATION_FORM_URLENCODED.withCharset("utf-8"));
        builder.setContentEncoding("content-type=UTF-8");
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

        StatsUtils utils=new StatsUtils();
        return utils.store_stats(stats_token,get_stats());
    }

    private String get_client_ip(String ip)  {
        String result=ip;
        try{
            if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
                InetAddress inetAddress = InetAddress.getLocalHost();
                String ipAddress = inetAddress.getHostAddress();
                result = ipAddress;
            }
        }catch(Exception ex){
            result="unknown host";
        }
        return result;
    }

    private String get_header_value(String header){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        String value=rq.getHeader(header);
        return value==null?"unknowwn":value;
    }

    private String get_server_date(){
        DateTimeFormatter dtf=DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return OffsetDateTime.now().format(dtf);
    }

    private String get_user_name(){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        return rq.getRemoteUser();
    }

    private JsonElement get_user_search_request(){
        HttpSession ss=(HttpSession) ctx.getSession();
        if (this.get_urn().equals("/admin/search")) {
            StaffSearchBean sb=(StaffSearchBean) ss.getAttribute("search");
            if (sb!=null){
                return sb.toJson();
            }else{
                return null;
            }

        }else
        {
            return null;
        }
    }

    private String get_urn(){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        String urn=rq.getPathInfo();
        if (urn!=null)  {
            return urn;
        }else{
            return "";
        }
    }

    private String get_last_tracker_id(){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        Cookie[] cookies=rq.getCookies();
        if (cookies!=null){
            List<Cookie> cookies_list = Arrays.asList(rq.getCookies());
            List<Cookie> found=cookies_list.stream().filter(cookie->cookie.getName().equals("rnk-stats-tracker")).collect(Collectors.toList());
            if ((found!=null) && (found.size()>0)){
                return found.get(0).getValue();
            }else{
                return "-1";
            }
        }else{
            return "-1";
        }
    }

    private String get_stats() {
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        Map<String,Object> result=new HashMap<>();
        result.put( "jsp_page_name", Paths.get(rq.getServletPath()).getFileName().toString());
        result.put( "urn", get_urn());
        result.put( "client_ip", get_client_ip(rq.getRemoteAddr()));
        result.put( "browser_version", get_header_value("User-Agent"));
        result.put( "client_time",get_header_value("x-rnk-client-time-header"));
        result.put( "server_time",get_server_date());
        result.put( "user_name",get_user_name());
        result.put( "user_country",rq.getLocale().getCountry());
        result.put( "user_searched_for",get_user_search_request());
        result.put( "last_tracker_id",get_last_tracker_id());


        return new Gson().toJson(result);
    }
}

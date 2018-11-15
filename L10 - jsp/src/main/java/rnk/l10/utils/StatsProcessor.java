package rnk.l10.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gwt.user.client.Cookies;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.core.util.IOUtils;
import rnk.l10.entities.beans.SearchResultCacheItem;
import rnk.l10.entities.beans.StaffSearchBean;
import rnk.l12.util.StatsUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                JsonParser parser=new JsonParser();
                JsonObject object=(JsonObject) parser.parse(new JsonReader(new InputStreamReader(stream, "UTF-8")));
                result = object.get("stats_id").getAsInt();
            }
        }

        return result;
    }

    private String getURL(HttpServletRequest req) {

        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80
//        String contextPath = req.getContextPath();   // /mywebapp
//        String servletPath = req.getServletPath();   // /servlet/MyServlet
//        String pathInfo = req.getPathInfo();         // /a/b;c=123
//        String queryString = req.getQueryString();          // d=789

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

//        url.append(contextPath).append(servletPath);
//
//        if (pathInfo != null) {
//            url.append(pathInfo);
//        }
//        if (queryString != null) {
//            url.append("?").append(queryString);
//        }
        return url.toString();
    }

    public Integer store_statsx() throws ServletException, IOException{

        String stats_token=ctx.getServletContext().getInitParameter("stats-token");
        if ((stats_token==null)||(stats_token.isEmpty())){
            throw new ServletException("Необходимо определить маркер статистики");
        }

        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        HttpPost post=new HttpPost(getURL(rq)+"/stats");

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
        return DateUtils.formatDate(new Date(System.currentTimeMillis()));
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
        result.put( "client_time",get_header_value("Date"));
        result.put( "server_time",get_server_date());
        result.put( "user_name",get_user_name());
        result.put( "user_country",rq.getLocale().getCountry());
        result.put( "user_searched_for",get_user_search_request());
        result.put( "last_tracker_id",get_last_tracker_id());


        return new Gson().toJson(result);
    }
}

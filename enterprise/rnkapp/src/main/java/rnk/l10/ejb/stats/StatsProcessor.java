package rnk.l10.ejb.stats;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import rnk.l10.ejb.stats.IStats;
import rnk.l10.entities.beans.StaffSearchBean;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StatsProcessor {

    @PersistenceContext(unitName = "RNK_PU")
    private EntityManager em;


    @EJB
    private IStats stats;

    public StatsProcessor(){}

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

    public Integer store_stats(PageContext ctx) throws ServletException, IOException{

        String stats_token=ctx.getServletContext().getInitParameter("stats-token");
        if ((stats_token==null)||(stats_token.isEmpty())){
            throw new ServletException("Необходимо определить маркер статистики");
        }

        return stats.store(stats_token,get_stats(ctx));
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

    private String get_header_value(String header, PageContext ctx){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        String value=rq.getHeader(header);
        return value==null?"unknowwn":value;
    }

    private String get_server_date(){
        DateTimeFormatter dtf=DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return OffsetDateTime.now().format(dtf);
    }

    private String get_user_name(PageContext ctx){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        return rq.getRemoteUser();
    }

    private JsonElement get_user_search_request(PageContext ctx){
        HttpSession ss=(HttpSession) ctx.getSession();
        if (this.get_urn(ctx).equals("/admin/search")) {
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

    private String get_urn(PageContext ctx){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        String urn=rq.getPathInfo();
        if (urn!=null)  {
            return urn;
        }else{
            return "";
        }
    }

    private String get_last_tracker_id(PageContext ctx){
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

    private String get_stats(PageContext ctx) {
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        Map<String,Object> result=new HashMap<>();
        result.put( "jsp_page_name", Paths.get(rq.getServletPath()).getFileName().toString());
        result.put( "urn", get_urn(ctx));
        result.put( "client_ip", get_client_ip(rq.getRemoteAddr()));
        result.put( "browser_version", get_header_value("User-Agent", ctx));
        result.put( "client_time",get_header_value("x-rnk-client-time-header",ctx));
        result.put( "server_time",get_server_date());
        result.put( "user_name",get_user_name(ctx));
        result.put( "user_country",rq.getLocale().getCountry());
        result.put( "user_searched_for",get_user_search_request(ctx));
        result.put( "last_tracker_id",get_last_tracker_id(ctx));


        return new Gson().toJson(result);
    }
}

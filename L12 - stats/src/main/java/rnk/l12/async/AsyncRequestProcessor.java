package rnk.l12.async;

import org.apache.log4j.Logger;
import rnk.l12.util.StatsUtils;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AsyncRequestProcessor implements Runnable{
    private static final Logger logger = Logger.getLogger(AsyncRequestProcessor.class.getName());
    private AsyncContext ctx;

    public AsyncRequestProcessor(){

    }

    public AsyncRequestProcessor(AsyncContext ctx){
        this.ctx=ctx;
    }

    public void run(){
        HttpServletRequest rq=(HttpServletRequest) ctx.getRequest();
        ServletResponse rs=ctx.getResponse();

        String command=(String)rq.getAttribute("async_command");
        switch (command){
            case "store_stats":{
                try{
                    Integer stats_id=(new StatsUtils()).store_stats(rq.getParameter("app_token"),rq.getParameter("stats"));

                    rs.setCharacterEncoding("utf-8");
                    rs.setContentType("application/json");

                    rs.getWriter().println(String.format("{\"stats_id\":%d}",stats_id));
                }catch(Exception ex){
                    logger.error("stats error(async):",ex);
                }

                return;
            }

            case "control_stats":{
                try{
                    String service_password=rq.getServletContext().getInitParameter("stats-control-password");
                    String cmd=rq.getParameter("c");
                    String password=rq.getParameter("p");
                    String subject=rq.getParameter("s");

                    String response=(new StatsUtils()).control_stats(service_password,password, cmd,subject);

                    rs.setCharacterEncoding("utf-8");
                    rs.setContentType("application/json");

                    rs.getWriter().println(String.format("{\"result\":%s}",response));
                }catch(Exception ex){
                    logger.error("stats control error(async):",ex);
                }
                return;
            }

            default: return;
        }
    }
}

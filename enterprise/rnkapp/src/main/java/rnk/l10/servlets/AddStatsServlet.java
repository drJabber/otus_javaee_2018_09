package rnk.l10.servlets;

import org.apache.log4j.Logger;
import rnk.l10.async.AsyncStatsWrapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@WebServlet(value = "/stats", loadOnStartup = 1,
            displayName="collect statistics", initParams = {
                @WebInitParam(name="stats-control-password", value="qdvF91w0_")
            },
        asyncSupported = true
)
public class AddStatsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddStatsServlet.class.getName());

    @Override
    public void init() throws  ServletException{
        ThreadPoolExecutor executor=new ThreadPoolExecutor(100,200,50000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        this.getServletContext().setAttribute("executor",executor);
        logger.info("executor created");
    }

    @Override
    public void destroy(){
        ThreadPoolExecutor executor=(ThreadPoolExecutor) getServletContext().getAttribute("executor");
        logger.info("executor removed");
        executor.shutdown();
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws  ServletException{
        try{
            rq.setCharacterEncoding("UTF-8");
            logger.info(String.format("before store_stats: %s", rq.getParameter("stats")));
            (new AsyncStatsWrapper()).store_stats(rq);
            logger.info("after store_stats");
        }catch(Exception ex){
            logger.error("store stats error:",ex);
        }
    }


    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)  {
        try{
            (new AsyncStatsWrapper()).control_stats(rq);
        }catch(Exception ex){
            logger.error("stats control error:",ex);
        }
    }

}

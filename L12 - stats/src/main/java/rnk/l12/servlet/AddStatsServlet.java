package rnk.l12.servlet;

import org.apache.log4j.Logger;
import rnk.l12.async.AsyncStatsWrapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddStatsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddStatsServlet.class.getName());

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

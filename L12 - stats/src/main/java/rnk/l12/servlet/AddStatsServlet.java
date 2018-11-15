package rnk.l12.servlet;

import org.apache.log4j.Logger;
import rnk.l12.async.AsyncStatsWrapper;
import rnk.l12.util.StatsUtils;

import javax.persistence.*;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet
public class AddStatsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddStatsServlet.class.getName());
    private final StatsUtils stats = new StatsUtils();

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws  ServletException{
        try{
            logger.info("before store_stats");
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

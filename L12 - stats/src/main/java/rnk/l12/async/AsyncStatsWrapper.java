package rnk.l12.async;

import org.apache.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ThreadPoolExecutor;

public class AsyncStatsWrapper {
    private static final Logger logger = Logger.getLogger(AsyncStatsWrapper.class.getName());

    private void execute(HttpServletRequest rq){
        logger.info("request.startAsync: isAsyncSupperted="+rq.isAsyncSupported()+", isAsyncStarted: "+rq.isAsyncStarted());
        AsyncContext ctx=rq.startAsync();
        logger.info("before async call of AsyncRequestProcessor");
        ctx.addListener(new StatsAsyncListener());
        ctx.setTimeout(30000);

        ThreadPoolExecutor executor=(ThreadPoolExecutor)rq.getServletContext().getAttribute("executor");
        logger.info("immediately before execute(AsyncRequestProcessor)");
        executor.execute(new AsyncRequestProcessor(ctx));
        logger.info("after async call of AsyncRequestProcessor");
    }

    public void store_stats(HttpServletRequest rq){
        rq.setAttribute("async_command","store_stats");
        execute(rq);
    }

    public void control_stats(HttpServletRequest rq){
        rq.setAttribute("async_command","control_stats");
        execute(rq);
    }
}

package rnk.l12.async;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ThreadPoolExecutor;

public class AsyncStatsWrapper {

    private void execute(HttpServletRequest rq){
        AsyncContext ctx=rq.getAsyncContext();
        ctx.addListener(new StatsAsyncListener());
        ctx.setTimeout(30000);

        ThreadPoolExecutor executor=(ThreadPoolExecutor)rq.getServletContext().getAttribute("executor");
        executor.execute(new AsyncRequestProcessor(ctx));
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

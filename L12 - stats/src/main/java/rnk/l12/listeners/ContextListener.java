package rnk.l12.listeners;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//setup thread pool to execute async servlet code
@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ContextListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ThreadPoolExecutor executor=new ThreadPoolExecutor(100,200,50000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        sce.getServletContext().setAttribute("executor",executor);
        logger.info("executor created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ThreadPoolExecutor executor=(ThreadPoolExecutor) sce.getServletContext().getAttribute("executor");
        logger.info("executor removed");
        executor.shutdown();

    }
}

package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.Session;
import java.util.Queue;
import java.util.concurrent.*;

@WebListener
public class WSContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(WSContextListener.class.getName());
    private static Queue<Session> sessions=new ConcurrentLinkedDeque<>();
    private static int WSTIMEOUT=15;

    private class WorkerThread implements Runnable{
        @Override
        public void run(){
            logger.info("ws thread started");
            while (true){
                try{
                    if (sessions!=null){
                        (new Updater()).loadAndSend(sessions);
                    }
                    Thread.sleep(WSTIMEOUT*1000);
                    logger.info("ws thread after send");
                }catch(Exception ex){
                    logger.error("ws thread error", ex);
                }
            }
        }
    }


    private class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.info("ws thread rejected: "+r.toString());
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent e) {
        String timeout=e.getServletContext().getInitParameter("ws-update-timeout");

        WSTIMEOUT=Integer.parseInt(timeout);

        ThreadFactory tf=Executors.defaultThreadFactory();
        RejectedExecutionHandler rjh=new RejectedExecutionHandlerImpl();
        ThreadPoolExecutor executor=new ThreadPoolExecutor(10,50,
                15,TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(50),
                tf,rjh);

        e.getServletContext().setAttribute("ws_sessions",sessions);
        e.getServletContext().setAttribute("ws_executor",executor);
        logger.info("ws sessions list created");

        executor.execute(new WorkerThread());
        logger.info("ws threadpool started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor ) e.getServletContext().getAttribute("ws_executor");
        if (executor!=null){
            executor.shutdown();
        }
        logger.info("ws context destroyed");
    }
}

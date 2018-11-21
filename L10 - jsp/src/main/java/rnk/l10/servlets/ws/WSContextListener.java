package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.Session;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@WebListener
public class WSContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(WSContextListener.class.getName());
    private static Queue<Session> sessions=new ConcurrentLinkedDeque<>();
    private static final int WSTIMEOUT=50;

    static {
        new Thread(()->{
            while (true){
                if (sessions!=null){
                    WSUtils.loadAndSend(sessions);
                }
                try{
                    Thread.sleep(WSTIMEOUT*1000);
                }catch(Exception ex){
                    logger.error("ws thread error", ex);
                }
            }
        });
    }

    @Override
    public void contextInitialized(ServletContextEvent e) {
        e.getServletContext().setAttribute("ws_sessions",sessions);
        logger.info("ws sessions list created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {

    }
}

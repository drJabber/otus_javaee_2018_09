package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

public class Updater {
    private static final Logger logger = Logger.getLogger(Updater.class.getName());

    public void loadAndSend(Queue<Session> sessions){
        try{
            ArrayList<Session> closedSessions=new ArrayList<>();
            if (sessions.size()==0){
                logger.warn("ws sessions list empty");
            }

            UpdaterThread.resetCacheState();

            for (Session session: sessions) {
                if (!session.isOpen()){
                    closedSessions.add(session);
                    logger.error("ws session is closed:"+session.getId());
                }else{
                    HttpSession s=(HttpSession)session.getUserProperties().get("http-session");
                    ServletContext ctx=s.getServletContext();
                    ThreadPoolExecutor executor = (ThreadPoolExecutor )ctx.getAttribute("ws_executor");
                    Runnable worker=new UpdaterThread(session);
                    executor.execute(worker);
                }
            }

            sessions.removeAll(closedSessions);
        }catch(Exception ex){
            logger.error("ws load and send error:", ex);
        }
    }

    public void loadAndSend(Session session){
        try{
            if (!session.isOpen()){
                logger.error("ws session is closed:"+session.getId());
            }else{
                UpdaterThread.resetCache();
                UpdaterThread.resetCacheState();
                (new UpdaterThread(session)).run();//run synchronously
            }
        }catch(Exception ex){
            logger.error("ws (first) load and send error:", ex);
        }
    }

}

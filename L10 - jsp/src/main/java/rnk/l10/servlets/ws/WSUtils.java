package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

public class WSUtils {
    private static final Logger logger = Logger.getLogger(WSUtils.class.getName());

    public void loadAndSend(Queue<Session> sessions){
        try{
            ArrayList<Session> closedSessions=new ArrayList<>();
            if (sessions.size()==0){
                logger.warn("ws sessions list empty");
            }

            for (Session session: sessions) {
                if (!session.isOpen()){
                    closedSessions.add(session);
                    logger.error("ws session is closed:"+session.getId());
                }else{
                    HttpSession s=(HttpSession)session.getUserProperties().get("http-session");
                    ServletContext ctx=s.getServletContext();
                    ThreadPoolExecutor executor = (ThreadPoolExecutor )ctx.getAttribute("ws_executor");
                    Runnable worker=new WSThread(session);
                    executor.execute(worker);
                }
            }

            sessions.removeAll(closedSessions);
        }catch(Exception ex){
            logger.error("ws payload get error:", ex);
        }
    }
}

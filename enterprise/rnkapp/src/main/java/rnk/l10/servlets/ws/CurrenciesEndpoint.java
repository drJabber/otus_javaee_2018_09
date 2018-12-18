package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Queue;

@ServerEndpoint(value = "/curr",  encoders = {JsonEncoder.class}, configurator = Configurer.class)
public class CurrenciesEndpoint {
    private static final Logger logger = Logger.getLogger(NewsEndpoint.class.getName());

    private EndpointConfig config;

    private Queue<Session> getSessions(){
        HttpSession session = (HttpSession)config.getUserProperties().get("http-session");
        if (session!=null){
            ServletContext ctx=session.getServletContext();
            if (ctx!=null){
                return (Queue<Session>)ctx.getAttribute("ws_sessions");
            }
        }

        return null;
    }

    @OnMessage
    public void message(Session session, String msg) {
        //provided for completeness, in out scenario clients don't send any msg.
        try {
            logger.info("ws msg (currencies) " + msg + " from " + session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(Session session, EndpointConfig config){
        this.config=config;
	//configure worker
        session.getUserProperties().put("endpoint","currencies");
        session.getUserProperties().put("http-session",config.getUserProperties().get("http-session"));

        (new Updater()).loadAndSend(session);//immediately send data to client
        Queue<Session> sessions=getSessions();
        if (sessions!=null){
            sessions.add(session); //add session to sessions list
        }
        logger.info(String.format("ws curr open: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
    }

    @OnClose
    public void close(Session session){
        Queue<Session> sessions=getSessions();
        if (sessions!=null){
            getSessions().remove(session);
        }
        logger.info(String.format("ws curr close: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
    }

    @OnError
    public void error(Session session, Throwable e){
        logger.error("ws error:",e);
        Queue<Session> sessions=getSessions();
        if (sessions!=null){
            getSessions().remove(session);
        }
    }

}


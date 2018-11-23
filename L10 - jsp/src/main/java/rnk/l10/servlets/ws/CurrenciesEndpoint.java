package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;

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
        return (Queue<Session>)session.getServletContext().getAttribute("ws_sessions");
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
        getSessions().add(session);
        session.getUserProperties().put("endpoint","currencies");
        session.getUserProperties().put("isnew","Y");
        session.getUserProperties().put("http-session",config.getUserProperties().get("http-session"));
        (new Updater()).loadAndSend(getSessions());
        logger.info(String.format("ws curr open: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
    }

    @OnClose
    public void close(Session session){
        getSessions().remove(session);
        logger.info(String.format("ws curr close: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
    }

    @OnError
    public void error(Session session, Throwable e){
        logger.error("ws error:",e);
        getSessions().remove(session);
    }

}


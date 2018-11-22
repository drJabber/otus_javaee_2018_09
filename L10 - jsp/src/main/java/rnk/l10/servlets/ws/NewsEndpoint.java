package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;
import rnk.l10.startup.StartupServlet;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Queue;

@ServerEndpoint(value = "/news", encoders = {JsonEncoder.class},   configurator = WSConfigurator.class)
public class NewsEndpoint {
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
            logger.info("ws msg (news) " + msg + " from " + session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnOpen
    public void open(Session session, EndpointConfig config){
        this.config=config;
        getSessions().add(session);
        session.getUserProperties().put("endpoint","news");
        session.getUserProperties().put("isnew","Y");
        session.getUserProperties().put("http-session",config.getUserProperties().get("http-session"));
        (new WSUtils()).loadAndSend(getSessions());
        logger.info(String.format("ws news open: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
    }

    @OnClose
    public void close(Session session){
        getSessions().remove(session);
        logger.info(String.format("ws news close: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
    }

    @OnError
    public void error(Session session, Throwable e){
        getSessions().remove(session);
        logger.error("ws error:",e);
    }

}

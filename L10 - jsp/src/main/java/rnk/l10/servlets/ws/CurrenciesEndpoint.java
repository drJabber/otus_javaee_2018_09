package rnk.l10.servlets.ws;

import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Queue;

@ServerEndpoint(value = "/curr", configurator = WSConfigurator.class)
public class CurrenciesEndpoint {
    private static final Logger logger = Logger.getLogger(NewsEndpoint.class.getName());

    private EndpointConfig config;
    private Queue<Session> getSessions(){
        return (Queue<Session>)config.getUserProperties().get("http-session");
    }

    @OnOpen
    public void open(Session session, EndpointConfig config){
        logger.info(String.format("ws curr open: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
        this.config=config;
        getSessions().add(session);
        session.getUserProperties().put("endpoint","currencies");
    }

    @OnClose
    public void close(Session session){
        logger.info(String.format("ws curr close: sid=%s, q=%s, ", session.getId(), session.getQueryString()) );
        getSessions().remove(session);
    }

    @OnError
    public void error(Session session, Throwable e){
        getSessions().remove(session);
        logger.error("ws error:",e);
    }

}


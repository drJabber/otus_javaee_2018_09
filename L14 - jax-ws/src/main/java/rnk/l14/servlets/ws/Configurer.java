package rnk.l14.servlets.ws;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class Configurer extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        if (sec!=null){
            if (request!=null){
                HttpSession session=(HttpSession) request.getHttpSession();
                sec.getUserProperties().put("http-session", session);
            }else{
                sec.getUserProperties().put("http-session", null);
            }
        }
    }
}

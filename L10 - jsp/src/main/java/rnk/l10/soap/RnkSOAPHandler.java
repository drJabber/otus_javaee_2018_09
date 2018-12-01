package rnk.l10.soap;

import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.api.handler.MessageHandlerContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RnkSOAPHandler implements SOAPHandler<SOAPMessageContext> {
    private static final Logger logger = Logger.getLogger(RnkSOAPHandler.class.getName());

    public RnkSOAPHandler() {
        super();
        logger.info("Soap handler create:");
    }

    @Override
    public void close(MessageContext arg0) {
        logger.info("Soap handler close:");
    }

    @Override
    public boolean handleFault(SOAPMessageContext arg0) {
        logger.info("Soap handler fault:");
        return false;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        try{
            logger.info("Handle soap message:");
//            System.out.println("Handling soap message...");

            Map<String, List<String>> map = (Map<String, List<String>>)smc.get(MessageContext.HTTP_REQUEST_HEADERS);
            Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

            if (outboundProperty.booleanValue()) {
                logger.info("Outbound message:");
                //this is underlying http response object
                HttpServletResponse response = (HttpServletResponse) smc.get(MessageContext.SERVLET_RESPONSE);
                if (response!=null){
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.addHeader("Access-Control-Allow-Credentials", "false");
                    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type, SOAPAction, Access-Control-Allow-Headers, Access-Control-Response-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Origin");
                }
            } else {
                logger.info("Inbound message:");
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        logger.info("\nOutbound Soap handler getheaders:");
        return null;
    }

}

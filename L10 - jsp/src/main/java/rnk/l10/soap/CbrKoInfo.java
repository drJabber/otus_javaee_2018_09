package rnk.l10.soap;

import com.google.gson.*;
import org.apache.log4j.Logger;
import rnk.l10.soap.cbko.CreditOrgInfo;
import rnk.l10.utils.CbrHelper;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.xml.ws.handler.MessageContext.HTTP_REQUEST_METHOD;
import static javax.xml.ws.handler.MessageContext.QUERY_STRING;
import static javax.xml.ws.handler.MessageContext.PATH_INFO;

//see about CORS https://stackoverflow.com/questions/28434969/using-cors-filter-for-soap-service
@WebServiceProvider
@ServiceMode(value = Service.Mode.MESSAGE)
@BindingType(value=HTTPBinding.HTTP_BINDING)
@HandlerChain(file="rnk-soap-handler.xml")
public class CbrKoInfo implements Provider<Source> {
    private static final Logger logger = Logger.getLogger(CbrKoInfo.class.getName());

    private static String HTTP_METHOD_GET="GET";

    private static CreditOrgInfo ws=new CreditOrgInfo();

    @Resource
    private WebServiceContext wsCtx;

    @Override
    public Source invoke(Source request) {
        MessageContext ctx=wsCtx.getMessageContext();
        String method=(String) ctx.get(HTTP_REQUEST_METHOD);
        if (method.equalsIgnoreCase(HTTP_METHOD_GET)){
            return doGet(ctx);
        }else{
            logger.error("cbr service supports only GET");
            throw new UnsupportedOperationException("Only GET here");
        }
    }

    private Source doGet(MessageContext ctx){
        try {
            String query=(String)ctx.get(PATH_INFO);
            return makeResult(dispatchQuery(ctx, parseQuery(query)));
        }catch(Exception ex){
            logger.error("cbr service error:",ex);
            return null;
        }

    }

    private CbrHelper parseQuery(String query){
        List<String> tokens= new ArrayList<>(Arrays.asList(query.split("/")));
        if (tokens.get(0).isEmpty()){
            tokens.remove(0);
        }
        if (tokens.get(tokens.size()-1).isEmpty()){
            tokens.remove(tokens.size()-1);
        }

        Integer size=tokens.size();
        if (size>3){
            throw new UnsupportedOperationException("cbr service query too complex");
        }
        if (size==0){
            throw new UnsupportedOperationException("cbr service nothing to query");
        }

        Gson gson=new Gson();
        switch (size){
            case 1: return new CbrHelper(tokens.get(0),null,null) ;
            case 2: return new CbrHelper(tokens.get(0), tokens.get(1), null);
            case 3: return new CbrHelper(tokens.get(2), tokens.get(1), tokens.get(2));
            default: return null;
        }
    }


    private SOAPMessage cbrLastUpdate(MessageContext ctx) throws SOAPException {
        Gson gson=new Gson();
        XMLGregorianCalendar calendar=ws.getCreditOrgInfoSoap().lastUpdate();
        LocalDateTime dt=calendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
        DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String result=fmt.format(dt);

        SOAPFactory sf=SOAPFactory.newInstance();
        MessageFactory mf= MessageFactory.newInstance();
        SOAPMessage response=mf.createMessage();
        SOAPBody body=response.getSOAPBody();
        SOAPElement content=body.addChildElement("result");
        content.setValue(result);
        response.saveChanges();

        return response;

    }

    private SOAPMessage dispatchQuery(MessageContext ctx, CbrHelper query) throws SOAPException{
        switch(query.getMethod().toLowerCase()){
            case "lastupdate":{
                return cbrLastUpdate(ctx);
            }
            default:{
                throw new UnsupportedOperationException("cbr service function not implemented yet");
            }
        }
    }

    private Source makeResult(SOAPMessage message) throws TransformerConfigurationException, TransformerException {
        StringWriter sw=new StringWriter();
        TransformerFactory
                .newInstance()
                .newTransformer()
                .transform(new DOMSource(message.getSOAPPart()),new StreamResult(sw));

        return new StreamSource(new StringReader(sw.toString()));
    }
}

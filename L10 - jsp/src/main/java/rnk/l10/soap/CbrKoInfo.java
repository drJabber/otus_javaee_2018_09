package rnk.l10.soap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import rnk.l10.soap.cbko.CreditOrgInfo;

import javax.annotation.Resource;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static javax.xml.ws.handler.MessageContext.HTTP_REQUEST_METHOD;
import static javax.xml.ws.handler.MessageContext.QUERY_STRING;

@WebServiceProvider
@ServiceMode(value = Service.Mode.MESSAGE)
@BindingType(value=HTTPBinding.HTTP_BINDING)
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
            String query=(String)ctx.get(QUERY_STRING);
            return makeResult(dispatchQuery(parseQuery(query)));
        }catch(Exception ex){
            logger.error("cbr service error:",ex);
            return null;
        }

    }

    private JsonObject parseQuery(String query){
        List<String> tokens= Arrays.asList(query.split("/"));
        if (tokens.get(0).isEmpty()){
            tokens.remove(0);
        }
        if (tokens.get(-1).isEmpty()){
            tokens.remove(-1);
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
            case 1:return (JsonObject) gson.toJsonTree(String.format("{\"function\":\"%s\"}", tokens.get(0)));
            case 2:return (JsonObject) gson.toJsonTree(String.format("{\"function\":\"%s\",\"param1\":\"%s\"}", tokens.get(0), tokens.get(1)));
            case 3:return (JsonObject) gson.toJsonTree(String.format("{\"function\":\"%s\",\"param1\":\"%s\",\"param2\":\"%s\"}", tokens.get(0), tokens.get(1), tokens.get(2)));
            default: return null;
        }
    }


    private JsonElement cbrLastUpdate(){
        Gson gson=new Gson();
        XMLGregorianCalendar calendar=ws.getCreditOrgInfoSoap().lastUpdate();
        LocalDateTime dt=calendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
        DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String result=fmt.format(dt);
        return (JsonObject)gson.toJsonTree(String.format("{\"r\":\"%s\"}", result));

    }

    private JsonElement dispatchQuery(JsonObject query){
        switch(query.get("function").getAsString().toLowerCase()){
            case "lastupdate":{
                return cbrLastUpdate();
            }
            default:{
                throw new UnsupportedOperationException("cbr service function not implemented yet");
            }
        }
    }

    private Source makeResult(JsonElement response){
        return new StreamSource();
    }
}

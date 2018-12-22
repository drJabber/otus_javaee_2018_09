package rnk.t01.rest;

import org.apache.log4j.Logger;
import rnk.l10.ejb.snils.Validator;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestServiceImpl {
    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private static InitialContext defaultCtx;

//    @EJB(lookup = "java:global/rnkt02/Validator!rnk.t02.soap.Validator")
//    java:global/rnkapp/SnilsValidator!rnk.l10.ejb.snils.Validator, java:global/rnkapp/SnilsValidator
    @EJB(lookup = "java:global/rnkapp/SnilsValidator!rnk.l10.ejb.snils.Validator")
    Validator validator;

    static {
        try {
            defaultCtx = new InitialContext();
        } catch (NamingException ex) {
            logger.error(ex);
        }
    }

    @GET
    @Path("/start")
    public Map<String, String> startTest() throws NamingException{
        Map<String, String> result=new HashMap<>();
        Boolean b= validator.check("001-280-213-70");
        result.put("snilscherker.check('001-280-213-70')",b.toString());
        b = validator.check("001-280-213-74");
        result.put("snilscherker.check('001-280-213-74')",b.toString());
        return result;
    }
}
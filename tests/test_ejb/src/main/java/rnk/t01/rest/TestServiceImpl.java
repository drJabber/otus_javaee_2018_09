package rnk.t01.rest;

import org.apache.log4j.Logger;
import rnk.l10.soap.SnilsChecker;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestServiceImpl {
    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private static InitialContext defaultCtx;

//   @EJB(lookup = "ejb/rnk.l10.snilschecker")
//   SnilsChecker bean;

    static {
        try {
            defaultCtx = new InitialContext();
        } catch (NamingException ex) {
            logger.error(ex);
        }
    }

    public TestServiceImpl(){
        logger.info("rest impl visited");
    }

    @GET
    @Path("/start")
    public Map<String, String> startTest() throws NamingException{
        rnk.l10.soap.SnilsChecker snilsChecker=(rnk.l10.soap.SnilsChecker)defaultCtx.lookup("java:global/rnkt02/SnilsChecker!rnk.t02.soap.SnilsCheckerWebService");
        Map<String, String> result=new HashMap<>();
//        Boolean b=bean.check("001-280-213-70");
        Boolean b=true;
        result.put("snilscherker.check('001-280-213-70')",b.toString());
//        b=bean.check("001-280-213-74");
        result.put("snilscherker.check('001-280-213-74')",b.toString());
        return result;
    }
}

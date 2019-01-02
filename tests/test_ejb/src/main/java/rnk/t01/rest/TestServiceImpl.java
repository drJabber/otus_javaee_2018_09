package rnk.t01.rest;

import com.google.gson.Gson;
import com.sun.enterprise.security.ee.auth.login.ProgrammaticLogin;
import org.apache.log4j.Logger;
import rnk.l10.ejb.snils.Validator;
import rnk.l10.ejb.credits.RnkCreditAccounterV1;
import rnk.l10.ejb.credits.RnkCreditAccounterV2;
import rnk.l10.ejb.staff.IStaffFacade;
import rnk.l10.ejb.staff.StaffEntityFacade;
import rnk.l10.rest.model.AccountingParams;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestServiceImpl {
    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

//    @Context
//    private InitialContext defaultCtx;

    private static InitialContext defaultCtx;

//    @EJB(lookup = "java:global/rnkapp/SnilsValidator!rnk.l10.ejb.snils.Validator")
//    Validator validator;
//
//    @EJB(lookup = "java:global/rnkapp/AnnuitetCreditAccounter!rnk.l10.ejb.credits.RnkCreditAccounterV2")
//    RnkCreditAccounterV2 accounter2;
//
//    @EJB(lookup = "java:global/rnkapp/DifferentialCreditAccounter!rnk.l10.ejb.credits.RnkCreditAccounterV1")
//    RnkCreditAccounterV1 accounter1;
//
//    @EJB(lookup = "java:global/rnkapp/StaffFacade!rnk.l10.ejb.staff.IStaffFacade")
//    IStaffFacade utils;

    static {
        try {
            defaultCtx = new InitialContext();
        } catch (NamingException ex) {
            logger.error(ex);
        }
    }


    @GET
    @Path("/start")
    public Map<String, String> startTest(@Context HttpServletRequest requestCtx) throws  ServletException {

        Map<String, String> result=new HashMap<>();
        try{
            ProgrammaticLogin pl=new ProgrammaticLogin();

            Boolean l=pl.login("oldman","blah","RnkRealm",true);

            Validator validator=(Validator) defaultCtx.lookup("java:global/rnkapp/SnilsValidator!rnk.l10.ejb.snils.Validator");

            Boolean b= validator.check("001-280-213-70");
            result.put("snilscherker.check('001-280-213-70')",b.toString());
            b = validator.check("001-280-213-74");
            result.put("snilscherker.check('001-280-213-74')",b.toString());

            AccountingParams p=new AccountingParams();
            p.setNumberOfPeriods(12);
            p.setAmountOfCredit(5000000.0);
            p.setRate(0.003);

            RnkCreditAccounterV1 accounter1=(RnkCreditAccounterV1) defaultCtx.lookup("java:global/rnkapp/DifferentialCreditAccounter!rnk.l10.ejb.credits.RnkCreditAccounterV1");
            RnkCreditAccounterV2 accounter2=(RnkCreditAccounterV2) defaultCtx.lookup("java:global/rnkapp/AnnuitetCreditAccounter!rnk.l10.ejb.credits.RnkCreditAccounterV2");
            Gson gson=new Gson();

            List<Double> payments1=accounter1.computePayment(p);
            result.put("DifferentialCreditAccounterBean.computePayment(n=12, cr=5000000, r=0.003)",gson.toJson(payments1));

            List<Double> payments2=accounter2.computePayment(p);
            result.put("AnnuitetCreditAccounterBean.computePayment(n=12, cr=5000000, r=0.003)",gson.toJson(payments2));

            IStaffFacade utils=(IStaffFacade)defaultCtx.lookup("java:global/rnkapp/StaffFacade!rnk.l10.ejb.staff.IStaffFacade");


            StaffEntityFacade entity=utils.getStaff(-1);
            result.put("StaffFacade.getStaff(-1)",gson.toJson(entity));
            result.put("StaffFacade.getPersonWithMaxSalary()",gson.toJson(utils.getPersonWithMaxSalary()));


        }
        catch(Exception ex){
            throw new ServletException(ex);
        }finally {
            return result;
        }
    }
}
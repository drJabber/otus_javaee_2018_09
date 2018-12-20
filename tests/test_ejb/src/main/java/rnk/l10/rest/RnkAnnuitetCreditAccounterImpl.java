package rnk.l10.rest;

//import io.swagger.annotations.*;
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.servers.Server;
import org.apache.log4j.Logger;
//import rnk.l10.ejb.credits.AnnuitetCreditAccounterBean;
//import rnk.l10.ejb.credits.RnkCreditAccounterV1;
import rnk.l10.ejb.credits.RnkCreditAccounterV2;
import rnk.l10.rest.model.AccountingParams;

//import javax.ejb.EJB;
//import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Path("/v2/accounter")
@Produces(MediaType.APPLICATION_JSON)
public class RnkAnnuitetCreditAccounterImpl  {
    private static final Logger logger = Logger.getLogger(RnkAnnuitetCreditAccounterImpl.class.getName());


   @Inject @Default
   RnkCreditAccounterV2 bean;

    public RnkAnnuitetCreditAccounterImpl(){
        logger.info("rest impl visited");
    }

    @GET
    @Path("/compute")
    public List<Double> computePayment(
            @Valid
            @BeanParam AccountingParams params) {

                //если закомментировать этот код,
                //закомментировать инжект
                //и раскомментировать то, что ниже,
                //то все работает
                // это надо проделать в обоих ресурсах
                return bean.computePayment(params);
                
        // List<Double> result=new ArrayList<>();
        // int T=params.getNumberOfPeriods();
        // double Kr=params.getAmountOfCredit();
        // double R=params.getRate();
        // double P=Kr*R/(1-1/Math.pow((1+R),T));
        // for (int i=0; i<T;i++){
        //     result.add(P);
        // }
        // return result;
    }
}

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
import rnk.l10.rest.model.AccountingParams;

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
public class RnkAnnuitetCreditAccounterImpl implements  RnkCreditAccounter {
    private static final Logger logger = Logger.getLogger(RnkAnnuitetCreditAccounterImpl.class.getName());

    public RnkAnnuitetCreditAccounterImpl(){
        logger.info("rest impl visited");
    }

    @Override
    @GET
    @Path("/compute")
//    @Operation(
//            summary="Расчет аннуитетного платежа по кредиту",
//            tags = {"Расчет","Кредит","График"},
//            description="Расчет аннуитетного платежа по кредиту",
//            responses = {@ApiResponse(description = "Список сумм платежей", content = @Content(schema = @Schema(implementation = List.class))),
//                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
//                    @ApiResponse(responseCode = "404", description = "Параметр не найден")
//            }
//    )
    public List<Double> computePayment(
//            @Parameter(description = "Параметры расчета", required = true)
            @Valid
            @BeanParam AccountingParams params) {
        List<Double> result=new ArrayList<>();
        int T=params.getNumberOfPeriods();
        double Kr=params.getAmountOfCredit();
        double R=params.getRate();
        double P=Kr*R/(1-1/Math.pow((1+R),T));
        for (int i=0; i<T;i++){
            result.add(P);
        }
        return result;

    }
}

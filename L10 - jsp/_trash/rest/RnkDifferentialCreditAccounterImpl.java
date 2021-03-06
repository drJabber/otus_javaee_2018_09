package rnk.l10.rest;

import io.swagger.annotations.*;
import rnk.l10.rest.model.AccountingParams;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Providers;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/accounter")
@Produces(MediaType.APPLICATION_JSON)
@Api(tags="Accounter")
@SwaggerDefinition(
        info = @Info(
                title = "RESTful API расчета платежей по кредиту",
                description = "RESTful API расчета платежей по кредиту, версия 1",
                version = "1.0",
                termsOfService = "используйте на здоровье",
                contact = @Contact(
                        name = "Денис", email = "dJabber@gmail.com",
                        url = "https://nowhere.no"),
                license = @License(
                        name = "No license 2.0",
                        url = "http://nowhere.no")),
        tags = {@Tag(name="Accounter", description = "Расчет платежей по кредиту (дифференцированный платеж)")},
        host = "localhost:8080",
        basePath = "/api",
        schemes = {SwaggerDefinition.Scheme.HTTP},
        externalDocs = @ExternalDocs(
                value = "Справка по использованию",
                url = "https://github.com/drJabber/otus_javaee_2018_09/tree/master/L10%20-%20jsp/_doc/rest.md")
)
public class RnkDifferentialCreditAccounterImpl implements  RnkCreditAccounter {
    @Context
    Application app; // provides access to application configuration information.

    @Context
    HttpHeaders headers; // provides access to HTTP header information either as a Map or as convenience methods.
    // Note that @HeaderParam can also be used to bind an HTTP header to a resource method parameter,
    // field, or bean property.

    @Context
    Request request; // provides a helper to request processing and is typically used with Response to dynamically build the response.

    @Context
    SecurityContext security; // provides access to security-related information about the current request.

    @Context
    Providers providers; // supplies information about runtime lookup of provider instances based on a set of search criteria.

    @Context
    ResourceInfo resourceInfo; // supplies info about current resource


    @Override
    @GET
    @Path("/compute")
    @ApiOperation("Расчет дифференцированного платежа по кредиту")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "numberOfPeriods", value = "Количество периодов выплаты", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "amountOfCredit", value = "Размер кредита", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "rate", value = "Ставка по кредиту за период (0.05=5%)", dataType = "double", paramType = "query")
    })
    public List<Double> computePayment(@Valid @BeanParam AccountingParams params) {
        List<Double> result=new ArrayList<>();
        int T=params.getNumberOfPeriods();
        double Kr=params.getAmountOfCredit();
        double R=params.getRate();
        for (int i=0; i<T;i++){
            result.add(Kr*(1+R*(T-i))/T);
        }
        return result;

    }
}

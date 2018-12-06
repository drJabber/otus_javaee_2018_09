package rnk.l10.rest.model;


//import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

@Data
@FieldNameConstants

public class AccountingParams {
    @NotNull(message = "Credit term should not be empty")
    @DecimalMin(value = "1",message = "Credti term should be greater than 0")
    @QueryParam(value="t")
    private int numberOfPeriods;

    @NotNull(message = "Credit amount should not be empty")
    @Positive(message = "Credit amount shold be greater than 0")

    @QueryParam(value="cr")
    private double amountOfCredit;

    @NotNull(message = "Credit rate shoul not be empty")
    @Positive(message = "Credit rate should be greater than 0")
    @Max(value = 1,message = "Credit rate should be less than 1")
    @QueryParam(value="r")
    private double rate;
}

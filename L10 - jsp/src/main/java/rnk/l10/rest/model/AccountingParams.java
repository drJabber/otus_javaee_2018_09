package rnk.l10.rest.model;


import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.ws.rs.QueryParam;

@Data
@FieldNameConstants
public class AccountingParams {
    @ApiParam(value=FIELD_NUMBER_OF_PERIODS, required=true)
    @QueryParam(value=FIELD_NUMBER_OF_PERIODS)
    private int numberOfPeriods;

    @ApiParam(value=FIELD_AMOUNT_OF_CREDIT, required = true)
    @QueryParam(value=FIELD_AMOUNT_OF_CREDIT)
    private double amountOfCredit;

    @ApiParam(value=FIELD_RATE, required = true)
    @QueryParam(value=FIELD_RATE )
    private double rate;
}

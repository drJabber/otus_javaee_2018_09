package rnk.l10.rest.model;


import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.io.Serializable;

@Data
@FieldNameConstants
public class AccountingParams implements Serializable {
    private Integer numberOfPeriods;
    private Double amountOfCredit;
    private Double rate;
}

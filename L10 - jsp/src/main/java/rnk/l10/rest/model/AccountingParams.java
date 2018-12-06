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
    @NotNull(message = "Укажите срок кредита")
    @DecimalMin(value = "1",message = "Срок кредита - положительное число")
    @DecimalMax(value = "99",message = "Скрок кредита - не более 99 лет")
    @QueryParam(value="t")
    private Integer numberOfPeriods;

    @NotNull(message = "Укажите сумму кредита")
    @Positive(message = "Сумма кредита - положительное число")
    @Max(value = 1000000000,message = "Скрок кредита - не более 99 лет")
    @QueryParam(value="cr")
    private Double amountOfCredit;

    @NotNull(message = "Укажите кредитную ставку")
    @Positive(message = "Кредитная ставка - положительное число")
    @Max(value = 1,message = "Кредитная ставка не должна быть больше 1")
    @DecimalMin(value = "0.001",message = "Кредитная ставка должна быть больше 0")
    @QueryParam(value="r")
    private Double rate;
}

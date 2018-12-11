package rnk.l10.rest.model;


//import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Количество периодов кредита", required = true, allowableValues = "больше 0", example = "12")
    private Integer numberOfPeriods;

    @NotNull(message = "Укажите сумму кредита")
    @Positive(message = "Сумма кредита - положительное число")
    @Max(value = 1000000000,message = "Сумма кредита - не более 1000000000")
    @QueryParam(value="cr")
    @Schema(description = "Сумма кредита", required = true, allowableValues = "больше 0", example = "1000000")
    private Double amountOfCredit;

    @NotNull(message = "Укажите кредитную ставку")
    @Positive(message = "Кредитная ставка - положительное число")
    @Max(value = 1,message = "Кредитная ставка не должна быть больше 1")
    @DecimalMin(value = "0.001",message = "Кредитная ставка должна быть больше 0")
    @QueryParam(value="r")
    @Schema(description = "Ставка кредита, %в месяц", required = true, allowableValues = "больше 0, меньше 1", example = "0.003=0.3%")
    private Double rate;
}

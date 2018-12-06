package rnk.l10.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDto {
    ErrorTypeEnum code;
    List<FieldErrorDTO> errors;
}


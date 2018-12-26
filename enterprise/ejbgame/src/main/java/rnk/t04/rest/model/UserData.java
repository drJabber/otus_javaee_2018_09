package rnk.t04.rest.model;


import lombok.Data;
import lombok.experimental.FieldNameConstants;
import java.io.Serializable;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;


@Data
@FieldNameConstants
public class UserData implements Serializable{
    @NotNull(message = "User name sould not be empty")
    @FormParam(value = "login-text")
    @Size(max=30, message="User name max length is 30")
    private String login;

    @NotNull(message = "Input your guess please")
    @FormParam(value = "guess-text")
    @DecimalMin(value = "0", message = "Guess shoul be in [0..9]")
    @DecimalMax(value = "9", message = "Guess shoul be in [0..9]")
    private Integer value;
}

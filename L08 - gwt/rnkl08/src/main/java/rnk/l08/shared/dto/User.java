package rnk.l08.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(prefix="")
public class User implements IsSerializable {

    @NotNull
    @Size(min=4, message = "Login min length is 4")
    private String login;

    @Size(min=4, message = "Password min length is 4")
    private String password;

    private String session;

    private Date expires;
}

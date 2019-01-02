package rnk.l10.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.QueryParam;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo implements Serializable {
    private String login;
    private String password;
    private String redirectTo;
}

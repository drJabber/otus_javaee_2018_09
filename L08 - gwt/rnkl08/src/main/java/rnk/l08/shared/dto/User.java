package rnk.l08.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

    private Integer role=-1;


//    public User(){
//        this.login="";
//        this.password="";
//    }
//
//    public User(String login, String password){
//        this.login=login;
//        this.password=password;
//    }
//
//    public String getLogin(){
//        return login;
//    }
//
//    public String getPassword(){
//        return password;
//    }
}

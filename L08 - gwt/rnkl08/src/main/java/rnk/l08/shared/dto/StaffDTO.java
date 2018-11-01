package rnk.l08.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(prefix="")
public class StaffDTO implements IsSerializable {
    private Integer id;
    private String fio;
    private String position;
    private String departament;
    private String role;
    private Integer salary;
    private String login;
    private String password;
    private Integer createPassword;
}

package rnk.l08.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.view.client.ProvidesKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(prefix="")
public class StaffDTO implements IsSerializable,Comparable<StaffDTO> {
    private Integer id;
    private String fio;
    private String position;
    private String departament;
    private String role;
    private Integer salary;
    private String login;
    private String password;
    private String passwd_hash;
    private String passwd_salt;
    private Integer createPassword;

    @Override
    public int compareTo(StaffDTO o) {
      return (o == null || o.fio == null) ? -1 : -o.fio.compareTo(fio);
    }

    public boolean equals(StaffDTO o) {
      if (o instanceof StaffDTO) {
        return id == ((StaffDTO) o).id;
      }
      return false;
    } 
    
    
}

package rnk.l08.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@FieldNameConstants(prefix="")
public class StaffDTO implements IsSerializable,Comparable<StaffDTO> {
    @NotNull
    private Integer id=-1;
    @NotNull(message="Поле не должно быть пустым")
    private String fio="";
    @NotNull(message="Поле не должно быть пустым")
    private String position="";
    @NotNull(message="Поле не должно быть пустым")
    private String departament="";
    @NotNull(message="Поле не должно быть пустым")
    private String role="";
    @Min(value = 0,message="Поле должно быть больше 0")
    private Integer salary=1;
    @NotNull(message="Поле не должно быть пустым")
    private String login="";
    @NotNull(message="Поле не должно быть пустым")
    private String password="hashed";
    private String passwd_hash="";
    private String passwd_salt="";
    private Integer createPassword=0;

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

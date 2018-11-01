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
public class StaffDTO implements IsSerializable,Comparable<StaffDTO> {
    private Integer id;
    private String fio;
    private String position;
    private String departament;
    private String role;
    private Integer salary;
    private String login;
    private String password;
    private Integer createPassword;

    public static final ProvidesKey<StaffDTO> KEY_PROVIDER = new ProvidesKey<StaffDTO>() {
      @Override
      public Object getKey(StaffDTO item) {
        return item == null ? null : item.getId();
      }
    };  
    
    @Override
    public int compareTo(StaffDTO o) {
      return (o == null || o.fio == null) ? -1 : -o.fio.compareTo(fio);
    }

    @Override
    public boolean equals(StaffDTO o) {
      if (o instanceof StaffDTO) {
        return id == ((StaffDTO) o).id;
      }
      return false;
    } 
    
    
}

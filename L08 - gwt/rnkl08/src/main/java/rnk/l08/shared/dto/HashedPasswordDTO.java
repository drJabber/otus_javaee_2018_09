package rnk.l08.shared.dto;


import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashedPasswordDTO implements IsSerializable {
    private String passwdhash;
    private String passwdsalt;
}

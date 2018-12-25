package rnk.l10.entities;

import lombok.Data;
import rnk.l10.entities.json.DepartamentEntityJsonAdapter;
import rnk.l10.entities.json.PositionEntityJsonAdapter;
import rnk.l10.entities.json.RoleEntityJsonAdapter;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class StaffEntity implements Serializable {
    private Integer id;
    private String fio;
    private String email;

    @JsonbTypeAdapter(PositionEntityJsonAdapter.class)
    private PositionEntity position;
    @JsonbTypeAdapter(value= DepartamentEntityJsonAdapter.class)
    private DepartamentEntity departament;
    @JsonbTypeAdapter(RoleEntityJsonAdapter.class)
    private RoleEntity role;
    private Integer salary;
    private String login;
    private Date birthDate;
    private String gender;
    private String passwd_hash;
    private String passwd_salt;

    @JsonbTransient
    private Integer position_id0;

    @JsonbTransient
    private Integer departament_id0;

    @JsonbTransient
    private Integer role_id0;

    @Override
    public String toString(){
        String s="";
        Set<AuthorityEntity> auths=role.getAuthorities();
        for (AuthorityEntity auth: auths) {
            s=s+auth.getAuthority()+", ";
        }
        if (!s.isEmpty())
        {
            s=s.substring(0,s.length()-2);
        }
        return String.format("%d - %s: %s at %s; role: %s(%s)",id,fio,position.getPosition(),departament.getDepartament(),role.getRole(),s);
    }

}

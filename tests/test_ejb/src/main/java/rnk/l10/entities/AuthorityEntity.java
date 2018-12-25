package rnk.l10.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AuthorityEntity  implements Serializable {
    private Integer id;

    private String authority;

    @JsonIgnoreProperties("authorities")
    private Set<RoleEntity> roles;

    public String toString(){
        return authority;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer value){
        id=value;
    }

    public String getAuthority(){
        return authority;
    }

    public void setAuthority(String value){
        authority=value;
    }

    public Set<RoleEntity> getRoles(){
        return roles;
    }

    public void setRoles(Set<RoleEntity> value){
        roles=value;
    }
}


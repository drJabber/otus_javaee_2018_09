package rnk.l10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RoleEntity implements Serializable {
    private Integer id;
    private String role;
    @JsonIgnoreProperties("authorities")
    private Set<AuthorityEntity> authorities;

    @Override
    public String toString(){
        return role;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer value){
        id=value;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String value){
        role=value;
    }

    public Set<AuthorityEntity> getAuthorities(){
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> value){
        authorities=value;
    }
}



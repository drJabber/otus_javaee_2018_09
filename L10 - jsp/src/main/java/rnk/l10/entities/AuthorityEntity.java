package rnk.l10.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="authorities",schema="public")
public class AuthorityEntity {
    @Id
    @Column(name="id")
    private Integer id;

    @NotNull
    @Size(max=200)
    @Column(name="authority")
    private String authority;

    @ManyToMany(
            mappedBy="authorities"
    )
    private Set<RoleEntity> roles=new HashSet<>();

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


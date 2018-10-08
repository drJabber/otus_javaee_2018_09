package rnk.l03.jpa_entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="roles",schema="public")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="id")
    private Integer id;

    @NotNull
    @Size(max=200)
    @Column(name="role")
    private String role;



    @ManyToMany(fetch=FetchType.LAZY,
            cascade={CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name="role_auth",
            joinColumns = {@JoinColumn(name="role_id")},
            inverseJoinColumns = {@JoinColumn(name="auth_id")})
    private Set<AuthorityEntity> authorities=new HashSet<>();

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



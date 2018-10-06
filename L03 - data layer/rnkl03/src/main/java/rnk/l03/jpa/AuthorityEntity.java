package rnk.l03.jpa;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @ManyToMany(fetch=FetchType.LAZY,
            cascade={CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name="role_auth",
            joinColumns = {@JoinColumn(name="role_id")},
            inverseJoinColumns = {@JoinColumn(name="auth_id")})
    private Set<AuthorityEntity> authorities=new HashSet<>();
}


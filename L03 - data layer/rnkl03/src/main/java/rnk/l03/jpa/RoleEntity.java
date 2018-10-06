package rnk.l03.jpa;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.HashSet;

@Data
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
                },
            mappedBy="roles"
    )
    private Set<RoleEntity> roles=new HashSet<>();
}



package rnk.l03.jpa_entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="staff",schema="public")
public class StaffEntity {
    @Id
    @SequenceGenerator(name="staff_id_seq",sequenceName = "staff_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="staff_id_seq")
    @Column(name="id",updatable=false)
    private Integer id;

    @NotNull
    @Size(max=500)
    @Column(name="fio")
    private String fio;

    @ManyToOne
    @JoinColumn(name="position_id")
    private PositionEntity position;

    @ManyToOne
    @JoinColumn(name="departament_id")
    private DepartamentEntity departament;

    @ManyToOne
    @JoinColumn(name="role_id")
    private RoleEntity role;

    @Column(name="salary")
    private Integer salary;

    @NotNull
    @Size(max=200)
    @Column(name="login")
    private String login;

    @NotNull
    @Size(max=500)
    @Column(name="passwd_hash")
    private String passwd_hash;

    @NotNull
    @Size(max=100)
    @Column(name="passwd_salt")
    private String passwd_salt;


    private Integer position_id;
    private Integer departament_id;
    private Integer role_id;

}

package rnk.l03.jpa;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="staff",schema="public")
public class StaffEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="id")
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

}

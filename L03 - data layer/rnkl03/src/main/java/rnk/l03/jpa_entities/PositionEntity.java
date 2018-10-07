package rnk.l03.jpa_entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="positions",schema="public")
public class PositionEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="id")
    private Integer id;

    @NotNull
    @Size(max=500)
    @Column(name="position")
    private String position;

    @ManyToOne
    @JoinColumn(name="default_dept_id")
    private DepartamentEntity default_departament;

    @ManyToOne
    @JoinColumn(name="head_id")
    private PositionEntity head;

    @ManyToOne
    @JoinColumn(name="default_role_id")
    private RoleEntity default_role;

    @Column(name="default_salary")
    private Integer default_salary;
}

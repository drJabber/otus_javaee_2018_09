package rnk.l10.entities;


import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@FieldNameConstants
@Entity
@Table(name="POSITIONS",schema="PUBLIC")
public class PositionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="id")
    private Integer id;

    @NotNull
    @Size(max=500)
    @Column(name="POSITION")
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


    private Integer default_dept_id0;
    private Integer head_id0;
    private Integer default_role_id0;
}

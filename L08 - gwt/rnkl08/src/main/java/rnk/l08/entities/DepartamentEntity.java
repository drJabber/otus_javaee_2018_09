package rnk.l08.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="departaments",schema="public")
public class DepartamentEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="id")
    private Integer id;

    @NotNull
    @Size(max=500)
    @Column(name="departament")
    private String departament;

    @ManyToOne
    @JoinColumn(name="head_dept_id")
    private DepartamentEntity head_dept;

    @ManyToOne
    @JoinColumn(name="head_of_dept_id")
    private PositionEntity head_of_dept;

    @Column(name="default_salary")
    private Integer default_salary;

    @NotNull
    @Size(max=200)
    @Column(name="town")
    private String town;


    private Integer head_dept_id0;
    private Integer head_of_dept_id0;

}

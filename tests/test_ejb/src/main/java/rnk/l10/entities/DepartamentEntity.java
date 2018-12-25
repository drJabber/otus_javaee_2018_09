package rnk.l10.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class DepartamentEntity  implements Serializable {
    private Integer id;
    private String departament;
    private DepartamentEntity head_dept;
    private PositionEntity head_of_dept;
    private Integer default_salary;
    private String town;

    private Integer head_dept_id0;
    private Integer head_of_dept_id0;

}

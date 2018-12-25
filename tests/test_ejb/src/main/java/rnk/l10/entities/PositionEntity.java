package rnk.l10.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class PositionEntity  implements Serializable {
    private Integer id;
    private String position;
    private DepartamentEntity default_departament;
    private PositionEntity head;
    private RoleEntity default_role;
    private Integer default_salary;


    private Integer default_dept_id0;
    private Integer head_id0;
    private Integer default_role_id0;
}

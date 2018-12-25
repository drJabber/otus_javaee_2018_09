package rnk.l10.ejb.staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import rnk.l10.entities.StaffEntity;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StaffEntityFacade implements Serializable {
    private Integer id;
    private String login;
    private String fio;
    private String gender;
    private String position;
    private String departament;
    private String role;
    private String email;
}

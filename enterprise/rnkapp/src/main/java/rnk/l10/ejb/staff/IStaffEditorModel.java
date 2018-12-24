package rnk.l10.ejb.staff;

import rnk.l10.entities.DepartamentEntity;
import rnk.l10.entities.GenderEntity;
import rnk.l10.entities.PositionEntity;
import rnk.l10.entities.RoleEntity;
import rnk.l10.rest.model.StaffDto;

import java.util.List;

@Remote
public interface IStaffEditorModel {
    String getPage();
    void setPage(String page);
    String getSubmitMethod();
    void setSubmitMethod(String submitMethod);
    String getSubmitPage();
    void setSubmitPage(String submitPage);
    String getCancelPage();
    void setCancelPage(String cancelPage);
    StaffDto getStaff();

    List<DepartamentEntity> getDepartaments();
    List<PositionEntity> getPositions();
    List<RoleEntity> getRoles();
    List<GenderEntity> getGenders();

    void initialize(String id);

}

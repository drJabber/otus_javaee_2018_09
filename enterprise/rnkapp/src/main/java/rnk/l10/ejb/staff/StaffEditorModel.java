package rnk.l10.ejb.staff;

import rnk.l10.entities.DepartamentEntity;
import rnk.l10.entities.GenderEntity;
import rnk.l10.entities.PositionEntity;
import rnk.l10.entities.RoleEntity;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.rest.model.StaffDto;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;

@Stateful
public class StaffEditorModel implements IStaffEditorModel{

    @EJB
    private IStaffUtils staffUtils;

    StaffDto staff;

    String page;
    String submitMethod;
    String submitPage;
    String cancelPage;

    public String getPage(){
        return page;
    }

    public void setPage(String page){
        this.page=page;
    }

    public String getSubmitMethod(){
        return submitMethod;
    }

    public void setSubmitMethod(String submitMethod){
        this.submitMethod=submitMethod;
    }

    public String getSubmitPage(){
        return submitPage;
    }

    public void setSubmitPage(String submitPage){
        this.submitPage=submitPage;
    }

    public String getCancelPage(){
        return cancelPage;
    }

    public void setCancelPage(String cancelPage){
        this.cancelPage=cancelPage;
    }

    public StaffDto getStaff(){
        return staff;
    }

    public void initialize(String id){
        staff=new StaffDto();
        staff.initialize(staffUtils);
        staff.initStaff(id);
    }

    public StaffEditorModel() throws RnkWebServiceException {
    }

    public List<DepartamentEntity> getDepartaments(){
        return staffUtils.getDepartaments();
    }

    public List<PositionEntity> getPositions(){
        return staffUtils.getPositions();
    }

    public List<RoleEntity> getRoles(){
        return staffUtils.getRoles();
    }

    public List<GenderEntity> getGenders(){
        return staffUtils.getGenders();
    }
}

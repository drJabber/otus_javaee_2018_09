package rnk.l10.rest.model;


//import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.log4j.Logger;
import rnk.l10.ejb.staff.IStaffUtils;
import rnk.l10.entities.StaffEntity;
import rnk.l10.exception.RnkWebServiceException;
//import rnk.l10.ejb.staff.StaffEditorModel;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StaffDto implements Serializable {
    private static final Logger logger = Logger.getLogger(StaffDto.class.getName());

    private StaffEntity staff;

    private IStaffUtils staffUtils;


    public StaffDto(){
        staff=new StaffEntity();
    }

    public void initialize(IStaffUtils utils){
        this.staffUtils=utils;
    }

    public void initStaff(String id){
        if (id!=null){
            staff=staffUtils.getStaff(Integer.parseInt(id));
        }
    }

    public void setId(Integer id){
        staff.setId(id);
    }

    public Integer getId(){
        if (staff!=null){
            return staff.getId();
        }else{
            return null;
        }
    }

    public void setLogin(String login){
        staff.setLogin(login);
    }

    public String getLogin(){
        if (staff!=null){
            return staff.getLogin();
        }else{
            return null;
        }
    }

    public void setFio(String fio){
        staff.setFio(fio);
    }

    public String getFio(){
        if (staff!=null){
            return staff.getFio();
        }else{
            return null;
        }
    }

    public void setEmail(String email){
        staff.setEmail(email);
    }

    public String getEmail(){
        if (staff!=null){
            return staff.getEmail();
        }else{
            return null;
        }
    }

    public void setSalary(Integer salary){
        staff.setSalary(salary);
    }

    public Integer getSalary(){
        if (staff!=null){
            return staff.getSalary();
        }else{
            return null;
        }
    }

    public void setRole(Integer role){
        staff.setRole_id0(role);
    }

    public Integer getRoleId(){
        if (staff!=null){
            return staff.getRole().getId();
        }else{
            return null;
        }
    }

    public void setPosition(Integer position){
        staff.setPosition_id0(position);
    }

    public Integer getPositionId(){
        if (staff!=null){
            return staff.getPosition().getId();
        }else{
            return null;
        }
    }

    public void setDepartament(Integer departament){
        staff.setDepartament_id0(departament);
    }

    public Integer getDepartamentId(){
        if (staff!=null){
            return staff.getDepartament().getId();
        }else{
            return null;
        }
    }

    public void setGender(String gender){
        staff.setGender(gender);
    }

    public String getGender(){
        if (staff!=null){
            return staff.getGender();
        }else{
            return null;
        }
    }

    public void setPassword(String password){
        staff.setPasswd_hash(password);
    }

    public void setBirthDate(String birthDate) throws RnkWebServiceException{
        try{
            SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd");
            staff.setBirthDate(parser.parse(birthDate));
        }catch (Exception ex){
            throw new RnkWebServiceException(ex);
        }
    }

    public String getBirthDate(){
        if (staff!=null){
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
            Date bd=staff.getBirthDate();
            if (bd!=null){
                return fmt.format(staff.getBirthDate());
            }else{
                return "";
            }

        }else{
            return null;
        }
    }

    public boolean isNew(){
        return staff.getId()==null;
    }

    public StaffEntity getStaff(){
        return staff;
    }
}

package rnk.l10.rest.model;


import rnk.l10.entities.StaffEntity;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.utils.StaffUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import java.text.SimpleDateFormat;

public class StaffDto {
    private StaffEntity staff;

    public StaffDto(){
        staff=new StaffEntity();
    }

    public StaffDto(String id)throws RnkWebServiceException {
        if (id!=null){
            staff= new StaffUtils().getStaff(Integer.parseInt(id));
        }
    }

    @PathParam(value="id")
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

    @FormParam(value="user_login")
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

    @FormParam(value="user_fio")
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

    @FormParam(value="user_email")
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

    @FormParam(value="user_salary")
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

    @FormParam(value="user_role")
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

    @FormParam(value="user_position")
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

    @FormParam(value="user_dept")
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

    @FormParam(value="user_gender")
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

    @FormParam(value="user_password")
    public void setPassword(String password){
        staff.setPasswd_hash(password);
    }

    @FormParam(value="user_birthdate")
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
            return fmt.format(staff.getBirthDate());
        }else{
            return null;
        }
    }

    public void save() throws RnkWebServiceException{
        if (isNew()){
            StaffUtils.addStaff(staff);
        }else{
            StaffUtils.saveStaff(staff);
        }
    }

    public boolean isNew(){
        return staff.id==null;
    }
}

package rnk.l10.ejb.staff;

import rnk.l10.entities.StaffEntity;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

@Singleton(name = "StaffFacade")
@Startup
public class StaffFacade implements IStaffFacade{
    @EJB
    IStaffUtils utils;

    @Override
    @RolesAllowed({"admin"})
    public StaffEntityFacade getStaff(Integer id){
        StaffEntity e=utils.getStaff(id);
        return new StaffEntityFacade(e.getId(),e.getLogin(),e.getFio(), e.getGender(),
                e.getPosition().getPosition(),e.getDepartament().getDepartament(),
                e.getRole().getRole(),e.getEmail());
    }


    @Override
    @RolesAllowed({"Accountant","admin"})
    public Double getMaxSalary(){
        return utils.getMaxSalary();
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public Double getMinSalary(){
        return utils.getMinSalary();
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public Double getAvgSalary(){
        return utils.getAvgSalary();
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public String getPersonWithMaxSalary(){
        return utils.getPersonWithMaxSalary();
    }

}

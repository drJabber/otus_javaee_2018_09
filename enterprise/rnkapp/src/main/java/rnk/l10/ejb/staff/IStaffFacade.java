package rnk.l10.ejb.staff;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

@Remote
public interface IStaffFacade {
    @RolesAllowed({"admin"})
    StaffEntityFacade getStaff(Integer id);
    @RolesAllowed({"Accountant","admin"})
    Double getMaxSalary();
    @RolesAllowed({"Accountant","admin"})
    Double getMinSalary();
    @RolesAllowed({"Accountant","admin"})
    Double getAvgSalary();
    @RolesAllowed({"Accountant","admin"})
    String getPersonWithMaxSalary();
}

package rnk.l10.ejb.staff;

import javax.ejb.Remote;

@Remote
public interface IStaffFacade {
    StaffEntityFacade getStaff(Integer id);
    Double getMaxSalary();
    Double getMinSalary();
    Double getAvgSalary();
    String getPersonWithMaxSalary();
}

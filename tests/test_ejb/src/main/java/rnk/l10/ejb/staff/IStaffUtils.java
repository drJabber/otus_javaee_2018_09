package rnk.l10.ejb.staff;

import rnk.l10.entities.*;

import java.util.List;

public interface IStaffUtils {
    Double getMaxSalary();
    Double getMinSalary();
    Double getAvgSalary();
    String getPersonWithMaxSalary();

    void saveStaff(StaffEntity staff);
    void addStaff(StaffEntity staff);
//    void save(StaffDto dto);
    void removeStaff(Integer id);

    StaffEntity getStaff(Integer id);

    List<DepartamentEntity> getDepartaments();
    List<PositionEntity> getPositions();

    List<RoleEntity> getRoles();
    List<GenderEntity> getGenders();

}
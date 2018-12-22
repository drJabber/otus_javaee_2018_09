package rnk.l10.ejb.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import rnk.l10.entities.DepartamentEntity;
import rnk.l10.entities.PositionEntity;
import rnk.l10.entities.RoleEntity;
import rnk.l10.entities.StaffEntity;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.rest.model.StaffDto;
//import rnk.l10.soap.ws.RnkWebServiceException_Exception;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.*;
import java.util.List;


@Singleton
@Startup
public class StaffUtils {
    @PersistenceContext(unitName = "RNK_PU")
    private EntityManager em;


    public Double getMaxSalary(){
        Query q = em.createQuery("select max(staff.salary) from StaffEntity staff ");
        return 1.0*(Integer)(q.getResultList()).get(0);
    }

    public Double getMinSalary(){
        Query q = em.createQuery("select min(staff.salary) from StaffEntity staff ");
        return 1.0*(Integer)(q.getResultList()).get(0);
    }

    public Double getAvgSalary(){
        Query q = em.createQuery("select avg(staff.salary) from StaffEntity staff ");
        return (Double) (q.getResultList()).get(0);
    }

    public String getPersonWithMaxSalary(){
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("public.get_max_salary_fio")
                    .registerStoredProcedureParameter(0,
                            String.class, ParameterMode.OUT);
            q.execute();

            return q.getOutputParameterValue(0).toString();
    }

    public StaffEntity getStaff(Integer id) {
        Query q = em.createQuery("select s from StaffEntity s where s.id=:id").setParameter("id",id);
        return (StaffEntity) q.getSingleResult();
    }

    public List<PositionEntity> getPositions(){
        Query q = em.createQuery("select p from PositionEntity p ");
        return (List<PositionEntity>)q.getResultList();
    }

    public List<DepartamentEntity> getDepartaments(){
        Query q = em.createQuery("select p from DepartamentEntity p ");
        return (List<DepartamentEntity>)q.getResultList();
    }


    public List<RoleEntity> getRoles(){
        Query q = em.createQuery("select p from RoleEntity p ");
        return (List<RoleEntity>)q.getResultList();
    }

    @Data
    @AllArgsConstructor
    private static class HashedPassword{
        private String passwdhash;
        private String passwdsalt;
    }


    private HashedPassword hashPassword(String password, EntityManager em){
        StoredProcedureQuery q = em
                .createStoredProcedureQuery("public.hash_password")
                .registerStoredProcedureParameter("p_password",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("o_passwd_hash", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("o_passwd_salt", String.class, ParameterMode.OUT);

        q.setParameter("p_password",password);
        q.execute();

        Object p_hash=q.getOutputParameterValue("o_passwd_hash");
        Object p_salt=q.getOutputParameterValue("o_passwd_salt");

        return new HashedPassword((String)p_hash,(String)p_salt);
    }


    public void saveStaff(StaffEntity staff) {
        StaffEntity s=em.find(StaffEntity.class,staff.getId());
        PositionEntity position=em.find(PositionEntity.class, staff.getPosition_id0());
        DepartamentEntity dept=em.find(DepartamentEntity.class, staff.getDepartament_id0());
        RoleEntity role=em.find(RoleEntity.class, staff.getRole_id0());

        staff.setPosition(position);
        staff.setDepartament(dept);
        staff.setRole(role);
        if ((s.getPasswd_hash()==null)&&(s.getPasswd_hash().isEmpty())){
            HashedPassword hp=hashPassword(staff.getPasswd_hash(),em);

            staff.setPasswd_hash(hp.getPasswdhash());
            staff.setPasswd_salt(hp.getPasswdsalt());
        }else{
            staff.setPasswd_hash(s.getPasswd_hash());
            staff.setPasswd_salt(s.getPasswd_salt());
        }

        em.merge(staff);
    }

    public void addStaff(StaffEntity staff) {
        PositionEntity position=em.find(PositionEntity.class, staff.getPosition_id0());
        DepartamentEntity dept=em.find(DepartamentEntity.class, staff.getDepartament_id0());
        RoleEntity role=em.find(RoleEntity.class, staff.getRole_id0());

        staff.setPosition(position);
        staff.setDepartament(dept);
        staff.setRole(role);

        HashedPassword hp=hashPassword(staff.getPasswd_hash(),em);
        staff.setPasswd_hash(hp.getPasswdhash());
        staff.setPasswd_salt(hp.getPasswdsalt());

        em.persist(staff);
    }

    public void save(StaffDto dto){
        if (dto.isNew()){
            addStaff(dto.getStaff());
        }else{
            saveStaff(dto.getStaff());
        }
    }

    public void removeStaff(Integer id) {
        StaffEntity s=em.find(StaffEntity.class,id);
        em.remove(s);
    }
}


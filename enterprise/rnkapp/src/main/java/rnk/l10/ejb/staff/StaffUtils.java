package rnk.l10.ejb.staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.log4j.Logger;
import rnk.l10.entities.*;
import rnk.l10.rest.model.StaffDto;
import rnk.l10.utils.HashUtils;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Singleton(name = "StaffUtils")
@Startup
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StaffUtils implements IStaffUtils{
    private static final Logger logger = Logger.getLogger(StaffEditorModel.class.getName());

    @PersistenceContext(unitName = "RNK_PU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct(){
        try{
            positions0=makePositions();
            departaments0=makeDepartaments();
            roles0=makeRoles();
            genders0=makeGenders();
        }catch (Exception ex){
            logger.error(ex);
        }
    }


    private List<PositionEntity> positions0=null;
    private List<DepartamentEntity> departaments0=null;
    private List<RoleEntity> roles0=null;
    private List<GenderEntity> genders0=null;

    private List<GenderEntity> makeGenders(){
        List<GenderEntity> result=new ArrayList<>();
        result.add(new GenderEntity("М","Мужской"));
        result.add(new GenderEntity("Ж","Женский"));
        return result;
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public Double getMaxSalary(){
        Query q = em.createQuery("select max(staff.salary) from StaffEntity staff ");
        return 1.0*(Integer)(q.getResultList()).get(0);
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public Double getMinSalary(){
        Query q = em.createQuery("select min(staff.salary) from StaffEntity staff ");
        return 1.0*(Integer)(q.getResultList()).get(0);
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public Double getAvgSalary(){
        Query q = em.createQuery("select avg(staff.salary) from StaffEntity staff ");
        return (Double) (q.getResultList()).get(0);
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public String getPersonWithMaxSalary(){
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("public.get_max_salary_fio")
                    .registerStoredProcedureParameter(1,
                            String.class, ParameterMode.OUT);
            q.execute();

            return q.getOutputParameterValue(1).toString();
    }

    private List<PositionEntity> makePositions(){
        Query q = em.createQuery("select p from PositionEntity p ");
        return (List<PositionEntity>)q.getResultList();
    }

    private List<DepartamentEntity> makeDepartaments(){
        Query q = em.createQuery("select p from DepartamentEntity p ");
        return (List<DepartamentEntity>)q.getResultList();
    }


    private List<RoleEntity> makeRoles(){
        Query q = em.createQuery("select p from RoleEntity p ");
        return (List<RoleEntity>)q.getResultList();
    }

    @Data
    @AllArgsConstructor
    private static class HashedPassword{
        private String passwdhash;
        private String passwdsalt;
    }


    private HashedPassword hashPassword(String password){

        HashUtils hu=new HashUtils();
        byte[] bsalt=hu.salt(24);
        String salt=hu.toBase64(bsalt);
        byte[] bpaswd=hu.hash(password,bsalt);
        String hashed_password=hu.toBase64(bpaswd);
        return new HashedPassword(hashed_password,salt);
    }


    @Override
    @RolesAllowed({"Accountant","admin"})
    public void saveStaff(StaffEntity staff) {
        StaffEntity s=em.find(StaffEntity.class,staff.getId());
        PositionEntity position=em.find(PositionEntity.class, staff.getPosition_id0());
        DepartamentEntity dept=em.find(DepartamentEntity.class, staff.getDepartament_id0());
        RoleEntity role=em.find(RoleEntity.class, staff.getRole_id0());

        staff.setPosition(position);
        staff.setDepartament(dept);
        staff.setRole(role);
        if ((staff.getPasswd_hash()!=null)&&(!staff.getPasswd_hash().isEmpty())){
            HashedPassword hp=hashPassword(staff.getPasswd_hash());

            staff.setPasswd_hash(hp.getPasswdhash());
            staff.setPasswd_salt(hp.getPasswdsalt());
        }else{
            staff.setPasswd_hash(s.getPasswd_hash());
            staff.setPasswd_salt(s.getPasswd_salt());
        }

        em.merge(staff);
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public void addStaff(StaffEntity staff) {
        PositionEntity position=em.find(PositionEntity.class, staff.getPosition_id0());
        DepartamentEntity dept=em.find(DepartamentEntity.class, staff.getDepartament_id0());
        RoleEntity role=em.find(RoleEntity.class, staff.getRole_id0());

        staff.setPosition(position);
        staff.setDepartament(dept);
        staff.setRole(role);

        HashedPassword hp=hashPassword(staff.getPasswd_hash());
        staff.setPasswd_hash(hp.getPasswdhash());
        staff.setPasswd_salt(hp.getPasswdsalt());

        em.persist(staff);
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public void save(StaffDto dto){
        dto.initialize(this);
        if (dto.isNew()){
            addStaff(dto.getStaff());
        }else{
            saveStaff(dto.getStaff());
        }
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public void removeStaff(Integer id) {
        StaffEntity s=em.find(StaffEntity.class,id);
        em.remove(s);
    }

    @Override
    public StaffEntity getStaff(Integer id) {
        Query q = em.createQuery("select s from StaffEntity s where s.id=:id").setParameter("id",id);
        return (StaffEntity) q.getSingleResult();
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public List<DepartamentEntity> getDepartaments(){
        return departaments0;
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public List<PositionEntity> getPositions(){
        return positions0;
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public List<RoleEntity> getRoles(){
        return roles0;
    }

    @Override
    @RolesAllowed({"Accountant","admin"})
    public List<GenderEntity> getGenders(){
        return genders0;
    }

}


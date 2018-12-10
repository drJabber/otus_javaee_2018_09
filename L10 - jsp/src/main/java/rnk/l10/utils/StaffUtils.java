package rnk.l10.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import rnk.l10.entities.DepartamentEntity;
import rnk.l10.entities.PositionEntity;
import rnk.l10.entities.RoleEntity;
import rnk.l10.entities.StaffEntity;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.soap.ws.RnkWebServiceException_Exception;

import javax.persistence.*;
import java.util.List;

public class StaffUtils {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    @FunctionalInterface
    private interface QueryExecutor{
        public Object execute(EntityManager em);
    }

    private static Object executeQuery(QueryExecutor executor) throws RnkWebServiceException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Object result=executor.execute(em);

            transaction.commit();

            return result;
        }catch(Exception ex){
            transaction.rollback();
            throw new RnkWebServiceException(ex);
        }
        finally {
            em.close();
        }
    }

    public static Double getMaxSalary()throws RnkWebServiceException{
        return (Double) executeQuery((em)->{
            Query q = em.createQuery("select max(staff.salary) from StaffEntity staff ");
            return 1.0*(Integer)(q.getResultList()).get(0);
        });
    }

    public static Double getMinSalary()throws RnkWebServiceException{
        return (Double) executeQuery((em)->{
            Query q = em.createQuery("select min(staff.salary) from StaffEntity staff ");
            return 1.0*(Integer)(q.getResultList()).get(0);
        });
    }

    public static Double getAvgSalary()throws RnkWebServiceException{
        return (Double) executeQuery((em)->{
            Query q = em.createQuery("select avg(staff.salary) from StaffEntity staff ");
            return (q.getResultList()).get(0);
        });
    }

    public static String getPersonWithMaxSalary()throws RnkWebServiceException{
        return (String) executeQuery((em)->{
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("get_max_salary_fio")
                    .registerStoredProcedureParameter(0,
                            String.class, ParameterMode.OUT);
            q.execute();

            return q.getOutputParameterValue(0).toString();
        });
    }

    public StaffEntity getStaff(Integer id) throws RnkWebServiceException {
        return (StaffEntity) executeQuery((em)->{
            Query q = em.createQuery("select s from StaffEntity s where s.id=:id").setParameter("id",id);
            return q.getSingleResult();
        });
    }

    public static List<PositionEntity> getPositions()throws RnkWebServiceException{
        return (List<PositionEntity>) executeQuery((em)->{
            Query q = em.createQuery("select p from PositionEntity p ");
            return (List<PositionEntity>)q.getResultList();
        });
    }

    public static List<DepartamentEntity> getDepartaments()throws RnkWebServiceException{
        return (List<DepartamentEntity>) executeQuery((em)->{
            Query q = em.createQuery("select p from DepartamentEntity p ");
            return (List<DepartamentEntity>)q.getResultList();
        });
    }


    public static List<RoleEntity> getRoles()throws RnkWebServiceException{
        return (List<RoleEntity>) executeQuery((em)->{
            Query q = em.createQuery("select p from RoleEntity p ");
            return (List<RoleEntity>)q.getResultList();
        });
    }

    @Data
    @AllArgsConstructor
    private static class HashedPassword{
        private String passwdhash;
        private String passwdsalt;
    }


    private static HashedPassword hashPassword(String password, EntityManager em){
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("hash_password")
                    .registerStoredProcedureParameter("p_pasword",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_passwd_hash", String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter("o_passwd_salt", String.class, ParameterMode.OUT);

            q.setParameter("p_password",password);
            q.execute();

            Object p_hash=q.getOutputParameterValue("o_passwd_hash");
            Object p_salt=q.getOutputParameterValue("o_passwd_salt");

            return new HashedPassword((String)p_hash,(String)p_salt);
    }


    public static void saveStaff(StaffEntity staff) throws  RnkWebServiceException{
        executeQuery((em)->{
            StaffEntity s=em.find(StaffEntity.class,staff.getId());
            PositionEntity position=em.find(PositionEntity.class, staff.getPosition_id0());
            DepartamentEntity dept=em.find(DepartamentEntity.class, staff.getDepartament_id0());
            RoleEntity role=em.find(RoleEntity.class, staff.getRole_id0());

            staff.setPosition(position);
            staff.setDepartament(dept);
            staff.setRole(role);
            if ((s.getPasswd_hash()==null)&&(s.getPasswd_hash().isEmpty())){
                HashedPassword hp=hashPassword(staff.getPasswd_hash(),em);

                staff.setPasswd_hash(s.getPasswd_hash());
                staff.setPasswd_salt(s.getPasswd_salt());
            }else{
                staff.setPasswd_hash(s.getPasswd_hash());
                staff.setPasswd_salt(s.getPasswd_salt());
            }

            em.merge(staff);

            return null;
        });
    }

    public static void removeStaff(Integer id) throws  RnkWebServiceException{
        return (List<RoleEntity>) executeQuery((em)->{
            Query q = em.createQuery("select p from RoleEntity p ");
            return (List<RoleEntity>)q.getResultList();
        });
    }
}


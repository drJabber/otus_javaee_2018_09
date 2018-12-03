package rnk.l10.utils;

import rnk.l10.exception.RnkWebServiceException;

import javax.persistence.*;

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

}


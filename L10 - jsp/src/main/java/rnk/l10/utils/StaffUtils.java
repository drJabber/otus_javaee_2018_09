package rnk.l10.utils;

import rnk.l10.soap.RnkWebServiceException;

import javax.persistence.*;
import javax.servlet.ServletException;

public class StaffUtils{
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    public Double getMaxSalary() throws RnkWebServiceException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("select max(staff.salary) from StaffEntity staff ");

            double result=1.0*(Integer)(q.getResultList()).get(0);

            transaction.commit();

            return result;
        }catch(Exception ex){
            transaction.rollback();
            throw new RnkWebServiceException(ex);
        }
        finally {
            em.close();
        }
    };

    public Double getMinSalary() throws RnkWebServiceException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("select min(staff.salary) from StaffEntity staff ");

            double result=1.0*(Integer)(q.getResultList()).get(0);

            transaction.commit();

            return result;
        }catch(Exception ex){
            transaction.rollback();
            throw new RnkWebServiceException(ex);
        }
        finally {
            em.close();
        }
    };

    public Double getAvgSalary() throws RnkWebServiceException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("select avg(staff.salary) from StaffEntity staff ");

            double result=(Double)(q.getResultList()).get(0);

            transaction.commit();

            return result;

        }catch(Exception ex){
            transaction.rollback();
            throw new RnkWebServiceException(ex);
        }
        finally {
            em.close();
        }
    };

    public String getPersonWithMaxSalary()throws RnkWebServiceException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("get_max_salary_fio")
                    .registerStoredProcedureParameter(0,
                            String.class, ParameterMode.OUT);
            q.execute();

            String mx= q.getOutputParameterValue(0).toString();

            transaction.commit();

            return mx;
        }catch(Exception ex){
            transaction.rollback();
            throw new RnkWebServiceException(ex);
        }
        finally {
            em.close();
        }
    };
}


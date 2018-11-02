package rnk.l08.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.log4j.Logger;
import rnk.l08.entities.*;
import rnk.l08.server.ServiceImpl;

import javax.persistence.*;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PasswordHelper {


    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

    public HashedPasswordEntity hashPassword(String password) throws ServletException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("hash_password")
                    .registerStoredProcedureParameter("p_password",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_passwd_hash", String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter("o_passwd_salt", String.class, ParameterMode.OUT);

            q.setParameter("p_password",password);
            q.execute();

            Object p_hash=q.getOutputParameterValue("o_passwd_hash");
            Object p_salt=q.getOutputParameterValue("o_passwd_salt");
            
            transaction.commit();
            
            return new HashedPasswordEntity((String)p_hash,(String)p_salt);
        }catch(Exception ex){
            transaction.rollback();
            logger.error("staff error:",ex);
            throw new ServletException();
        }
        finally {
            em.close();
        }
    }

    public PasswordHelper(){
    }
}

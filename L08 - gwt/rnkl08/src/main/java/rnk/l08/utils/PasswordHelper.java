package rnk.l08.utils;

import rnk.l08.entities.AuthorityEntity;
import rnk.l08.entities.DepartamentEntity;
import rnk.l08.entities.PositionEntity;
import rnk.l08.entities.RoleEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PasswordHelper {

    @Data
    @AllArgsConstructor
    public class HashedPassword{
        private String passwd_hash;
        private String passwd_salt;
    }

    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    public HashedPassword hashPassword(String password) throws ServletException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("hash_password")
                    .registerStoredProcedureParameter("p_pasword",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_passwd_hash", String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter("o_passwd_salt", String.class, ParameterMode.OUT);

            q.setParameter("p_password",session);
            q.execute();

            Object p_hash=q.getOutputParameterValue("o_passwd_hash");
            Object p_salt=q.getOutputParameterValue("o_passwd_salt");
            
            transaction.commit();
            
            return new HashedPassword((String)p_hash,(String)p_salt);
        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException();
        }
        finally {
            em.close();
        }
    }

    public PasswordHelper(){
    }
}

package rnk.l03.xml;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.RoleEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

public class RoleEntityAdapter extends XmlAdapter<String, RoleEntity> {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see


    @Override
    public RoleEntity unmarshal(String v) throws ServletException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            Query q1 = em.createQuery("select role from RoleEntity role where role.role=:role");
            q1.setParameter("role",v);
            List<RoleEntity> roles= q1.getResultList();
            transaction.commit();

            return roles.get(0);

        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException();
        }
        finally {
            em.close();
        }
    }

    @Override
    public String marshal(RoleEntity v) {
        return v==null ? null : v.getRole();
    }
}
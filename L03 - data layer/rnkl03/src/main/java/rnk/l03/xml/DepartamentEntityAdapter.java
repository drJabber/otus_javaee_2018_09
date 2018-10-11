package rnk.l03.xml;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.DepartamentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

public class DepartamentEntityAdapter extends XmlAdapter<String, DepartamentEntity> {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    @Override
    public DepartamentEntity unmarshal(String v) throws ServletException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            Query q1 = em.createQuery("select dept from DepartamentEntity dept where dept.departament=:departament");
            q1.setParameter("departament",v);
            List<DepartamentEntity> departaments= q1.getResultList();
            transaction.commit();

            return departaments.get(0);

        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException();
        }
        finally {
            em.close();
        }
    }

    @Override
    public String marshal(DepartamentEntity v) {
        return v==null ? null : v.getDepartament();
    }
}

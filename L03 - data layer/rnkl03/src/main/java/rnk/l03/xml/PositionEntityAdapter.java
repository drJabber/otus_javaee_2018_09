package rnk.l03.xml;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.PositionEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

public class PositionEntityAdapter extends XmlAdapter<String, PositionEntity> {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    @Override
    public PositionEntity unmarshal(String v) throws ServletException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            Query q1 = em.createQuery("select pos from PositionEntity pos where pos.position=:position");
            q1.setParameter("position",v);
            List<PositionEntity> positions= q1.getResultList();
            transaction.commit();

            return positions.get(0);

        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException();
        }
        finally {
            em.close();
        }
    }

    @Override
    public String marshal(PositionEntity v) {
        return v==null ? null : v.getPosition();
    }
}

package rnk.l03.json;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.PositionEntity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;
import javax.persistence.*;
import javax.servlet.ServletException;
import java.util.List;

public class PositionEntityJsonAdapter implements JsonbAdapter<PositionEntity, String> {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see


    @Override
    public String adaptToJson(PositionEntity p){
        return p.getPosition();
    }

    @Override
    public PositionEntity adaptFromJson(String adapted) throws ServletException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q1 = em.createQuery("select pos from PositionEntity pos where pos.position=:position");
            q1.setParameter("position", adapted.toString());
            List<PositionEntity> positions = q1.getResultList();
            transaction.commit();

            return positions.get(0);

        } catch (Exception ex) {
            transaction.rollback();
            throw new ServletException();
        } finally {
            em.close();
        }
    }
}
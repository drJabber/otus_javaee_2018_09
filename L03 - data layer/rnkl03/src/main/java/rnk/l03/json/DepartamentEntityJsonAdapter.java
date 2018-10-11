package rnk.l03.json;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.DepartamentEntity;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;
import javax.persistence.*;
import javax.servlet.ServletException;
import java.util.List;

public class DepartamentEntityJsonAdapter implements JsonbAdapter<DepartamentEntity, String> {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see


    @Override
    public String adaptToJson(DepartamentEntity p){
        return p.getDepartament();
    }

    @Override
    public DepartamentEntity adaptFromJson(String adapted) throws ServletException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            Query q1 = em.createQuery("select dept from DepartamentEntity dept where dept.departament=:departament");
            q1.setParameter("departament",adapted.toString());
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
}
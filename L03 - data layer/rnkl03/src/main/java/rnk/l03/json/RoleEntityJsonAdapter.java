package rnk.l03.json;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.AuthorityEntity;
import rnk.l03.jpa_entities.DepartamentEntity;
import rnk.l03.jpa_entities.PositionEntity;
import rnk.l03.jpa_entities.RoleEntity;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;
import javax.persistence.*;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleEntityJsonAdapter implements JsonbAdapter<RoleEntity, String> {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    @Override
    public String adaptToJson(RoleEntity p){
        return p.getRole();
    }

    @Override
    public RoleEntity adaptFromJson(String adapted) throws ServletException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            Query q1 = em.createQuery("select role from RoleEntity role where role.role=:role");
            q1.setParameter("role",adapted.toString());
            List<RoleEntity> roles= q1.getResultList();

            RoleEntity role=roles.get(0);

            Query q2 = em.createQuery("select auth from AuthorityEntity auth join fetch auth.roles r where r.role=:role");
            q2.setParameter("role",adapted.toString());
            List<AuthorityEntity> auths= q2.getResultList();

            Set<AuthorityEntity> aset=new HashSet<AuthorityEntity>();
            for (AuthorityEntity auth:auths) {
                aset.add(auth);
            }
            role.setAuthorities(aset);
//
            transaction.commit();


            return role;

        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException();
        }
        finally {
            em.close();
        }
    }
}
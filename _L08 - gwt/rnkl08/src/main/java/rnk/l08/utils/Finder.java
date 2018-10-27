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

public class Finder {

    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    public DepartamentEntity findDepartament(String dept) throws ServletException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            Query q1 = em.createQuery("select dept from DepartamentEntity dept where dept.departament=:departament");
            q1.setParameter("departament",dept);
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

    public PositionEntity findPosition(String position) throws ServletException{
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q1 = em.createQuery("select pos from PositionEntity pos where pos.position=:position");
            q1.setParameter("position", position);
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

    public RoleEntity findRole(String role) throws ServletException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();

            Query q1 = em.createQuery("select role from RoleEntity role where role.role=:role");
            q1.setParameter("role",role);
            List<RoleEntity> roles= q1.getResultList();

            RoleEntity r=roles.get(0);

            Query q2 = em.createQuery("select auth from AuthorityEntity auth join fetch auth.roles r where r.role=:role");
            q2.setParameter("role",role);
            List<AuthorityEntity> auths= q2.getResultList();

            Set<AuthorityEntity> aset=new HashSet<AuthorityEntity>();
            for (AuthorityEntity auth:auths) {
                aset.add(auth);
            }
            r.setAuthorities(aset);

            transaction.commit();

            return r;

        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException();
        }
        finally {
            em.close();
        }

    }

    public Finder(){
    }
}

package rnk.l03;


import rnk.l03.jpa_entities.DepartamentEntity;
import rnk.l03.jpa_entities.PositionEntity;
import rnk.l03.jpa_entities.StaffEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;




@WebServlet("/rnk_jpa")
public class RNKJPAServlet extends HttpServlet {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query q = em.createQuery(
                    "select staff "+
                       "from StaffEntity staff "+
                       "JOIN staff.departament dept " +
                       "join staff.position pos "+
                       "order by staff.id desc");
            List<StaffEntity> result = q.getResultList();
            try (PrintWriter pw = response.getWriter()){
                for (StaffEntity e : result){
                    pw.println(String.format("%d - %s: %s at %s",e.getId(),e.getFio(),e.getPosition().getPosition(),e.getDepartament().getDepartament()));
                }
            }
            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    }

    private byte[] get_salt() throws NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
    }

    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, 20000, 256));
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Map<String,String[]> parameters=request.getParameterMap();
            Query q;

            StaffEntity staffEntity = new StaffEntity();
            staffEntity.setFio(parameters.get("fio")[0]);

            q = em.createQuery("select dept from DepartamentEntity dept where dept.departament=:departament");
            q.setParameter("departament",parameters.get("dept")[0]);
            List<DepartamentEntity> departaments= q.getResultList();
            staffEntity.setDepartament(departaments.get(0));

            q = em.createQuery("select position from PositionEntity position where position.position=:position");
            q.setParameter("position",parameters.get("position")[0]);
            List<PositionEntity> positions= q.getResultList();
            staffEntity.setPosition(positions.get(0));

            staffEntity.setSalary(Integer.parseInt(parameters.get("salary")[0]));
            staffEntity.setLogin(parameters.get("login")[0]);

            byte[] salt=get_salt();
            String password=parameters.get("password")[0];
            staffEntity.setPasswd_hash(hash(password,salt));
            staffEntity.setPasswd_salt(Base64.getEncoder().encodeToString(salt));

            em.persist(staffEntity);
            em.flush();

            transaction.commit();
            response.getWriter().println("Person '" + parameters.get("fio")[0] + "' has been successfully created");
        }
        catch (Exception e){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    }


}


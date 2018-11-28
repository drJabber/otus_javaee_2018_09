package rnk.l10.servlets;


import rnk.l10.entities.*;

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
import java.util.Set;
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

            if (request.getParameterMap().containsKey("get_max_salary_fio")){
                StoredProcedureQuery q = em
                        .createStoredProcedureQuery("get_max_salary_fio")
                        .registerStoredProcedureParameter(0,
                                String.class, ParameterMode.OUT);
                q.execute();

                String mx= q.getOutputParameterValue(0).toString();

                response.setCharacterEncoding("utf-8");
                response.getWriter().println(mx.split(" ")[0]);


            }else
            {
                Query q = em.createQuery(
                        "select staff "+
                                "from StaffEntity staff "+
                                "order by staff.id desc");
                List<StaffEntity> result = q.getResultList();

                response.setCharacterEncoding("utf-8");
                try (PrintWriter pw = response.getWriter()){
                    for (StaffEntity e : result){
                        String s="";
                        RoleEntity role=e.getRole();
                        Set<AuthorityEntity> auths=role.getAuthorities();
                        for (AuthorityEntity auth: auths) {
                            s=s+auth.getAuthority()+", ";
                        }
                        if (!s.isEmpty())
                        {
                            s=s.substring(0,s.length()-2);
                        }
                        pw.println(String.format("%d - %s: %s at %s; role: %s(%s)",e.getId(),e.getFio(),e.getPosition().getPosition(),e.getDepartament().getDepartament(),role.getRole(),s));
                    }
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

            StaffEntity staffEntity = new StaffEntity();
            staffEntity.setFio(request.getParameter("fio"));

            Query q1 = em.createQuery("select dept from DepartamentEntity dept where dept.departament=:departament");
            q1.setParameter("departament",request.getParameter("dept"));
            List<DepartamentEntity> departaments= q1.getResultList();
            staffEntity.setDepartament(departaments.get(0));

            Query q2 = em.createQuery("select position from PositionEntity position where position.position=:position");
            q2.setParameter("position",request.getParameter("position"));
            List<PositionEntity> positions= q2.getResultList();
            staffEntity.setPosition(positions.get(0));

            Query q3 = em.createQuery("select role from RoleEntity role where role.role=:role");
            q3.setParameter("role",request.getParameter("role"));
            List<RoleEntity> roles= q3.getResultList();
            staffEntity.setRole(roles.get(0));

            staffEntity.setSalary(Integer.parseInt(request.getParameter("salary")));

            staffEntity.setLogin(request.getParameter("login"));

            byte[] salt=get_salt();
            String password=request.getParameter("password");
            staffEntity.setPasswd_hash(hash(password,salt));
            staffEntity.setPasswd_salt(Base64.getEncoder().encodeToString(salt));

            em.persist(staffEntity);
            em.flush();

            transaction.commit();
            response.getWriter().println("Person '" + request.getParameter("fio")+ "' has been successfully created");
        }
            catch (Exception e){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Integer staff_id=Integer.parseInt(request.getParameter("staff_id"));
            String new_fio=request.getParameter("new_fio");
            String new_dept=request.getParameter("new_dept");

            StaffEntity staffEntity = em.find(StaffEntity.class,staff_id);
            staffEntity.setFio(new_fio);

            Query q1 = em.createQuery("select dept from DepartamentEntity dept where dept.departament=:departament");
            q1.setParameter("departament",new_dept);
            List<DepartamentEntity> departaments= q1.getResultList();
            staffEntity.setDepartament(departaments.get(0));

            em.merge(staffEntity);
            em.flush();

            transaction.commit();
            response.getWriter().println("Person '" + new_fio+ "' has been successfully updated");
        }
        catch (Exception e){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Integer staff_id=Integer.parseInt(request.getParameter("staff_id"));

            StaffEntity staffEntity = em.find(StaffEntity.class,staff_id);
            String fio=staffEntity.getFio();

            em.remove(staffEntity);
            em.flush();

            transaction.commit();
            response.getWriter().println("Person '" + fio+ "' has been deleted");
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


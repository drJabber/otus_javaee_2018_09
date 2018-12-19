package rnk.l10.utils;

import org.apache.log4j.Logger;
import rnk.l10.servlets.AddStatsServlet;

import javax.persistence.*;
import javax.servlet.ServletException;

public class StatsUtils {
    public static final String PERSISTENCE_UNIT_NAME="RNK_PU";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(StatsUtils.class.getName());

    public int store_stats(String token, Object stats) throws ServletException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            logger.info("before store_stats stores data");
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("public.add_stats")
                    .registerStoredProcedureParameter("p_token",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_value",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_id", Integer.class, ParameterMode.OUT);

            q.setParameter("p_token",token);
            q.setParameter("p_value",stats);
            q.execute();

            Object stats_id=q.getOutputParameterValue("o_id");
            transaction.commit();

            logger.info("after store_stats stores data");
            return Integer.parseInt(stats_id.toString());
        }catch (Exception e){
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }
    }

    public void switch_stats(String subject, String sw) throws ServletException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("public.switch_stats")
                    .registerStoredProcedureParameter("p_token",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_switch",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_result",String.class, ParameterMode.OUT)
                    ;

            q.setParameter("p_token",subject);
            q.setParameter("p_switch",sw);
            q.execute();

            String r= q.getOutputParameterValue("o_result").toString();
            if (r.equals("fail")){
                throw new ServletException(String.format("switch % fails", subject));
            }
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException(ex);
        }finally {
            em.close();
        }
    }

    public String get_stats(String subject) throws ServletException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("public.get_stats_switch")
                    .registerStoredProcedureParameter("p_token",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_switch", String.class, ParameterMode.OUT);

            q.setParameter("p_token",subject);
            q.execute();

            String sw= q.getOutputParameterValue("o_switch").toString();
            transaction.commit();

            return sw;
        }catch(Exception ex){
            transaction.rollback();
            throw new ServletException(ex);
        }finally {
            em.close();
        }
    }

    public String control_stats(String service_password,String password, String command, String subject) throws  ServletException{
        if (service_password.equals(password)){
            switch (command){
                case "stats_on":{
                    switch_stats(subject,"on");
                    return "on";
                }
                case "stats_off":{
                    switch_stats(subject,"off");
                    return "off";
                }
                case "stats_get":{
                    return get_stats(subject);
                }
                default:{
                    return "fail";
                }
            }
        }else{
            return "fail";
        }
    }
}

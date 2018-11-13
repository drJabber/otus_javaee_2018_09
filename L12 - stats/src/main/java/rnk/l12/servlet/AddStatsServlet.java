package rnk.l12.servlet;

import org.apache.log4j.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet
public class AddStatsServlet extends HttpServlet {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(AddStatsServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws  ServletException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("add_stats")
                    .registerStoredProcedureParameter("p_token",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_value",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_id", Integer.class, ParameterMode.OUT);

            q.setParameter("p_token",rq.getParameter("app_token"));
            q.setParameter("p_value",rq.getParameter("stats"));
            q.execute();

            Object stats_id=q.getOutputParameterValue("o_id");
            transaction.commit();

            rs.setCharacterEncoding("utf-8");
            rs.setHeader("Content-Type","application/json");

            rs.getWriter().println(String.format("{\"stats_id\":%d}",stats_id));
            return;
        }catch (Exception e){
            transaction.rollback();
            logger.error("stats error:",e);
        }
        finally {
            em.close();
        }

    }

    private void switch_stats(String subject, String sw) throws ServletException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("switch_stats")
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

    private String get_stats(String subject) throws ServletException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("get_stats_switch")
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

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws  ServletException {
        try{
            String service_password=getInitParameter("stats-control-password");
            String command=rq.getParameter("c");
            String password=rq.getParameter("p");
            String subject=rq.getParameter("s");
            String response;
            if (password.equals(service_password)){
                switch (command){
                    case "stats_on":{
                        switch_stats(subject,"on");
                        response="on";
                        break;
                    }
                    case "stats_off":{
                        switch_stats(subject,"off");
                        response="off";
                        break;
                    }
                    case "stats_get":{
                        response=get_stats(subject);
                        break;
                    }
                    default:{
                        response="fail";
                        break;
                    }
                }
            }else{
                response="fail";
            }

            rs.getWriter().println(String.format("{\"result\":%s}",response));
        }catch(Exception ex){
            logger.error("stats control error:",ex);
        }
    }

}

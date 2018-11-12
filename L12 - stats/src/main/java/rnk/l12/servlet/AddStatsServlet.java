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

            JsonbConfig jsoncfg=new JsonbConfig()
                    .withFormatting(Boolean.TRUE)
                    .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);
            Jsonb jb=JsonbBuilder.create(jsoncfg);

            jb.toJson(stats_id,rs.getWriter());

            return;
        }catch (Exception e){
            transaction.rollback();
            logger.error("stats error:",e);
        }
        finally {
            em.close();
        }

    }
}

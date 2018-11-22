package rnk.l10.entities.beans;

import org.apache.log4j.Logger;
import rnk.l10.entities.StatsEntity;
import rnk.l12.entities.StatsReportColumn;

import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatsReportProducer {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(StatsReportProducer.class.getName());

    private List<StatsEntity> produceEntries(ServletContext ctx){
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        try {
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date=OffsetDateTime.now().format(dtf);
            String stats_token=ctx.getInitParameter("stats-token");

            et.begin();
            String query_string = String.format("select s from StatsEntity s where cast(s.date as date)=cast('%s' as date) and (token='%s') order by s.id desc", date,stats_token);
            Query q = em.createQuery(query_string);
            List<StatsEntity> l= q.getResultList();
            et.commit();

            return l;
        }catch (Exception ex){
            logger.error("error:",ex);
            et.rollback();
            return null;
        }finally {
            em.close();
        }
    }

    public  List<StatsEntity> produce(PageContext pageContext){
        return produceEntries(pageContext.getServletContext());
    }

    public List<StatsEntity> produce(ServletContext ctx){
        return produceEntries(ctx);
    }

}

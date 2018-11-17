package rnk.l12.util;

import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatsReportProducer {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(StatsReportProducer.class.getName());


    private HttpServletRequest rq;

    public StatsReportProducer(HttpServletRequest rq){
        this.rq=rq;
    }

    private void produceColumns(){
        List<String> list=new ArrayList<>();
        list.add("date");
        list.add("token");
        list.add("urn");
        list.add("user");
        list.add("country");
        list.add("ip");
        list.add("searched for");

        rq.setAttribute("statsBodyColumns",list);
    }

    private void produceEntries(){
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        try {
            String date=rq.getParameter("date");
            if (date==null){
                DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date=OffsetDateTime.now().format(dtf);
            }

            et.begin();
//            String query_string = String.format("select s from rnk.l12.entities.StatsEntity s where cast(s.date as date)=cast('%s' as date) order by s.id desc", date);
            String query_string = "select s from StatsEntity s";
            logger.info(query_string);
            Query q = em.createQuery(query_string);
            logger.info(String.format("list size: %d",q.getResultList().size()));
            rq.setAttribute("statsRecords",q.getResultList());
            et.commit();


        }catch (Exception ex){
            logger.error("error:",ex);
            et.rollback();
        }finally {
            em.close();
        }
    }

    private void produceHead(){
        String date=rq.getParameter("date");
        rq.setAttribute("title","RnK statistics");
        rq.setAttribute("context_path",rq.getContextPath());
        if ((date==null)||date.isEmpty()){
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date=OffsetDateTime.now().format(dtf);
        }
        rq.setAttribute("stats_date", date);
    }

    public  String produce(){
        produceHead();
        produceColumns();
        produceEntries();
        return "/META-INF/template/stats.ftl";
    }

}

package rnk.l12.util;

import org.apache.log4j.Logger;
import rnk.l12.entities.StatsReportColumn;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatsReportProducer {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(StatsReportProducer.class.getName());


    private HttpServletRequest request;
    private HttpServletResponse response;

    public void setRequest(HttpServletRequest request){
        this.request=request;
    }

    public void setResponse(HttpServletResponse response){
        this.response=response;
    }

    public StatsReportProducer(HttpServletRequest request){
        this.request = request;
    }


    private void produceColumns(){
        List<StatsReportColumn> list=new ArrayList<>();
        list.add(new StatsReportColumn("date","8%"));
        list.add(new StatsReportColumn("token","25%"));
        list.add(new StatsReportColumn("urn","10%"));
        list.add(new StatsReportColumn("user","7%"));
        list.add(new StatsReportColumn("country","7%"));
        list.add(new StatsReportColumn("ip","6%"));
        list.add(new StatsReportColumn("searched for","37%"));

        request.setAttribute("statsBodyColumns",list);
    }

    private void produceEntries(){
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        try {
            String date= request.getParameter("date");
            if (date==null){
                DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date=OffsetDateTime.now().format(dtf);
            }

            et.begin();
            String query_string = String.format("select s from StatsEntity s where cast(s.date as date)=cast('%s' as date) order by s.id desc", date);
//            String query_string = "select s from StatsEntity s";
            logger.info(query_string);
            Query q = em.createQuery(query_string);
            logger.info(String.format("list size: %d",q.getResultList().size()));
            request.setAttribute("statsRecords",q.getResultList());
            et.commit();


        }catch (Exception ex){
            logger.error("error:",ex);
            et.rollback();
        }finally {
            em.close();
        }
    }

    private void produceHead(){
        String date= request.getParameter("date");
        request.setAttribute("title","RnK статистика");
        request.setAttribute("context_path", request.getContextPath());
        if ((date==null)||date.isEmpty()){
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date=OffsetDateTime.now().format(dtf);
        }
        request.setAttribute("stats_date", date);
    }

    public  String produce(){
        produceHead();
        produceColumns();
        produceEntries();
        return "/META-INF/template/stats.ftl";
    }

}

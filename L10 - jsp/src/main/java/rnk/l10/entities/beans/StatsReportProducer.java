package rnk.l10.entities.beans;

import org.apache.log4j.Logger;
import rnk.l10.entities.StatsEntity;
import rnk.l12.entities.StatsReportColumn;

import javax.persistence.*;
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

//    private String date;
//
//    private String token;
//    private String urn;
//    private String user;
//    private String country;
//    private String ip;
//
//
//    private String searchedFor;
//
//    public String getDate() {return date; }
//    public String getToken() {return token; }
//    public String getUrn() {return urn; }
//    public String getUser() {return user; }
//    public String getCountry() {return country; }
//    public String getIp() {return ip; }
//    public String getSearchedFor() {return searchedFor; }
//
//    public void setDate(String date) {this.date = date;}
//    public void setToken(String token) {this.token = token;}
//    public void setUrn(String urn) {this.urn = urn;}
//    public void setUser(String user) {this.user = user;}
//    public void setCountry(String country) {this.country = country;}
//    public void setIp(String ip) {this.ip = ip;}
//    public void setSearchedFor(String searchedFor) {this.searchedFor = searchedFor;}
//
//
    //    private void produceColumns(){
//        List<StatsReportColumn> list=new ArrayList<>();
//        list.add(new StatsReportColumn("date","8%"));
//        list.add(new StatsReportColumn("token","25%"));
//        list.add(new StatsReportColumn("urn","10%"));
//        list.add(new StatsReportColumn("user","7%"));
//        list.add(new StatsReportColumn("country","7%"));
//        list.add(new StatsReportColumn("ip","6%"));
//        list.add(new StatsReportColumn("searched for","37%"));
//
//        request.setAttribute("statsBodyColumns",list);
//    }
//
    private List<StatsEntity> produceEntries(PageContext pageContext){
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        try {
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date=OffsetDateTime.now().format(dtf);
            String stats_token=pageContext.getServletContext().getInitParameter("stats-token");

            et.begin();
            String query_string = String.format("select s from StatsEntity s where cast(s.date as date)=cast('%s' as date) and (token='%s') order by s.id desc", date,stats_token);
//            String query_string = "select s from StatsEntity s";
//            logger.info(query_string);
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

//    private void produceHead(){
//        String date= request.getParameter("date");
//        request.setAttribute("title","RnK статистика");
//        request.setAttribute("context_path", request.getContextPath());
//        if ((date==null)||date.isEmpty()){
//            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            date=OffsetDateTime.now().format(dtf);
//        }
//        request.setAttribute("stats_date", date);
//    }
//
    public  List<StatsEntity> produce(PageContext pageContext){
        return produceEntries(pageContext);
    }

}

package rnk.l10.entities.beans;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Data;
import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rnk.l10.entities.StaffEntity;
import rnk.l10.entities.StatsEntity;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//@Data
public class StaffSearchBean {
    private static final Logger logger = Logger.getLogger(StaffSearchBean.class.getName());
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    private String login;
    private String fio;
    private String position;
    private String town;
    private Integer ageMin;
    private Integer ageMax;

    public void setLogin(String login){ this.login=login;}
    public void setFio(String fio){ this.fio=fio;}
    public void setTown(String town){ this.town=town;}
    public void setPosition(String position){ this.position=position;}
    public void setAgeMin(Integer ageMin){ this.ageMin=ageMin;}
    public void setAgeMax(Integer ageMax){ this.ageMax=ageMax;}

    public String getLogin() {
        return login;
    }

    public String getFio() {
        return fio;
    }

    public String getPosition() {
        return position;
    }

    public String getTown() {
        return town;
    }

    public Integer getAgeMax(){return (this.ageMax==0 || this.ageMin==0|| this.ageMax==null || this.ageMin==null)?null:this.ageMax;}
    public Integer getAgeMin(){return (this.ageMax==0 || this.ageMin==0|| this.ageMax==null || this.ageMin==null)?null:this.ageMin;}

    public boolean isEmpty(){
        return ((login==null) || (login.isEmpty())) &&
                ((fio ==null) || (fio.isEmpty())) &&
                ((position==null) || (position.isEmpty())) &&
                ((town==null) || (town.isEmpty())) &&
                (ageMax==null || ageMax==0) && (ageMin==null || ageMin==0);
    }

    public JsonElement toJson(){
        Gson gson=new Gson();
        return gson.toJsonTree(this);
    }

    public List<StaffEntity> find(PageContext context){
        if (!isEmpty()){
            EntityManager em=emf.createEntityManager();
            EntityTransaction et=em.getTransaction();
            try{
                et.begin();
                String query_string="select s from StaffEntity s %s where %s order by s.id desc";
                String likes="";
                String joins="";
                boolean has_period=false;

                if ((login!=null)&&(!login.isEmpty())) {
                    likes=likes.concat("(s.login like '%").concat(login).concat("%') and ");
                }
                if ((fio!=null)&&(!fio.isEmpty())) {
                    likes=likes.concat("(s.fio like '%").concat(fio).concat("%') and ");
                }
                if ((position!=null)&&(!position.isEmpty())) {
//                    likes=likes.concat(" 1=1 and ");
                    likes=likes.concat("(p.position like '%").concat(position).concat("%') and ");
                    joins=joins.concat("join fetch s.position p ");
                }
                if ((town!=null)&&(!town.isEmpty())) {
                    likes=likes.concat("(d.town like '%").concat(town).concat("%') and ");
                    joins=joins.concat("join fetch s.departament d ");
                }
                if ((ageMin!=null)&&(ageMax!=null)&&(ageMin>0)&&(ageMax>0)&&(ageMax>ageMin)) {
                    has_period=true;
                    Calendar calendar=new GregorianCalendar();
                    calendar.setTimeZone(TimeZone.getTimeZone("UTC+3"));
                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR,-ageMin);
                    Date bdMax=calendar.getTime();

                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR,-ageMax);
                    Date bdMin=calendar.getTime();
                    DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");

                    likes=likes.concat("(birth_date between '").concat(fmt.format(bdMin)).concat("' and '").concat(fmt.format(bdMax)).concat("') ");
                }
                if (!likes.isEmpty() && !has_period){
                    likes=likes.substring(0,likes.length()-4);
                }
                if (likes.isEmpty()){
                    et.rollback();
                    return null;
                }else{
                    logger.info(String.format(query_string, joins, likes));
                    String final_jpql=String.format(query_string, joins, likes);

                    SearchResultCache cache= (SearchResultCache) context.getServletContext().getAttribute("admin-search-cache");
                    if (cache!=null){
                        List<StaffEntity> result=cache.getQueryResult(final_jpql);
                        if (result!=null){
                            return result;
                        }
                    }

                    Query q = em.createQuery(final_jpql);

                    List<StaffEntity> l=q.getResultList();
                    et.commit();

                    context.getRequest().setAttribute("admin-search-result",new SearchResultCacheItem(final_jpql,l));
                    return l;
                }
            }catch (Exception ex){
                logger.error("error:",ex);
                et.rollback();
                return null;
            }finally{
                em.close();
            }

        }

        return null;

    }
}

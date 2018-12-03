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
import org.displaytag.pagination.PaginatedList;

//@Data
public class StaffDisplayBean{
    private static final Logger logger = Logger.getLogger(StaffDisplayBean.class.getName());
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final int PAGE_SIZE=10;

    private String login;
    private String fio;
    private String position;
    private String town;

    public void setLogin(String login){ this.login=login;}
    public void setFio(String fio){ this.fio=fio;}
    public void setTown(String town){ this.town=town;}
    public void setPosition(String position){ this.position=position;}

    public String getLogin() {        return login;    }

    public String getFio() {        return fio;    }

    public String getPosition() {        return position;    }

    public String getTown() {        return town;    }

    public JsonElement toJson(){
        Gson gson=new Gson();
        return gson.toJsonTree(this);
    }

    public Integet getPageSize(){
        return PAGE_SIZE;
    }

    public PaginatedList get(PageContext context){
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        try{
            et.begin();

            CriteriaQuery<Long> qc=cb.createQuery(Long.class);
            qc.select(cb.count(qc.from(StaffEntity.class)));

            Long full_size=em.createQuery(qc).getSingleResult();
            Long page_size=getPageSize();
            Long page_number=context.getRequest().getParameter("page");
            String sort=context.getRequest().getParameter("sort");
            String dir=context.getRequest().getParameter("dir");

            CriteriaQuery<StaffEntity> q=cb.createQuery(StaffEntity.class);
            Root<StaffEntity> from=q.from(StaffEntity.class);
            CriteriaQuery<StaffEntity> qe=q.select(from);
            TypedQuery<StaffEntity> typedQuery=em.createQuery(select).setFirstResult((page_number-1)*page_size).setMaxResults(page_size);;

            List<StaffEntity> l=typedQuery.getResultList();

            et.commit();

            return new JspPaginationAdapter(l,page_number,page_size,full_size,sort,dir);
        }catch (Exception ex){
            logger.error("error:",ex);
            et.rollback();
            return null;
        }finally{
            em.close();
        }
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

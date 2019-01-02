package rnk.l10.entities.beans;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import rnk.l10.entities.StaffEntity;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.jsp.PageContext;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.displaytag.pagination.PaginatedList;
import rnk.l10.entities.adapters.JspPaginationAdapter;

//@Data
@Stateful
@RolesAllowed({"admin"})
public class StaffDisplayBean{
    private static final Logger logger = Logger.getLogger(StaffDisplayBean.class.getName());

    @PersistenceContext(unitName = "RNK_PU")
//    private EntityManager em;
    private EntityManager em;

    private static final Integer PAGE_SIZE=10;

    private String login;
    private String fio;
    private String email;
    private String position;
    private String town;

    public void setLogin(String login){ this.login=login;}
    public void setFio(String fio){ this.fio=fio;}
    public void setEmail(String email){ this.email=email;}
    public void setTown(String town){ this.town=town;}
    public void setPosition(String position){ this.position=position;}

    public String getLogin() {        return login;    }

    public String getFio() {        return fio;    }

    public String getEmail() {        return email;    }

    public String getPosition() {        return position;    }

    public String getTown() {        return town;    }

    public JsonElement toJson(){
        Gson gson=new Gson();
        return gson.toJsonTree(this);
    }

    public Integer getPageSize(){
        return PAGE_SIZE;
    }

    public PaginatedList get(PageContext context){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        try{
            CriteriaQuery<Long> qc=cb.createQuery(Long.class);
            qc.select(cb.count(qc.from(StaffEntity.class)));

            Integer full_size=em.createQuery(qc).getSingleResult().intValue();
            Integer page_size=getPageSize().intValue();



            Integer page_number;
            try{
                page_number=Integer.parseInt(context.getRequest().getParameter("page"));
            }catch(Exception ex){
                page_number=1;
            }

            String sort=context.getRequest().getParameter("sort");
            String dir=context.getRequest().getParameter("dir");

            CriteriaQuery<StaffEntity> q=cb.createQuery(StaffEntity.class);
            Root<StaffEntity> from=q.from(StaffEntity.class);
            CriteriaQuery<StaffEntity> qe=q.select(from);
            TypedQuery<StaffEntity> typedQuery=em.createQuery(qe).setFirstResult((page_number-1)*page_size).setMaxResults(page_size);;

            List<StaffEntity> l=typedQuery.getResultList();

            return new JspPaginationAdapter(l,page_number,page_size,full_size,sort,dir);
        }catch (Exception ex){
            logger.log(Level.SEVERE,"error:",ex);
            return null;
        }finally{
        }
    }

}

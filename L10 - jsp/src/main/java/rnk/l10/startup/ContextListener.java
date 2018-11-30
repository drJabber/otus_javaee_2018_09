package rnk.l10.startup;

import org.apache.log4j.Logger;
import rnk.l10.entities.xml.StaffEntitiesList;
import rnk.l10.soap.CbrKoInfo;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.ws.Endpoint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener()
public class ContextListener implements ServletContextListener{
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(ContextListener.class.getName());

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        try {
            String webServiceUrl = "http://localhost:9999" + sce.getServletContext().getContextPath() + "/cbr";
            Endpoint.publish(webServiceUrl, new CbrKoInfo());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
      try{
          ServletContext context=sce.getServletContext();
          Object o=context.getAttribute("startup");
          logger.info("check startup object");
          if ((o!=null) && (o instanceof Startup)){
              Startup startup=(Startup)o;
              if (startup.getInitialized()==1){
                  store_staff_data_and_remove_from_db(get_filename(context));
              }
          }
      }catch(Exception ex){
          logger.error("Error while savind DB state to XML:", ex);
      }

    }

    private String get_filename(ServletContext context)throws NamingException {
        return context.getInitParameter("rnk-storage-path")+"/staff.xml";
    }

    private void store_staff_data_and_remove_from_db(String filename) throws ServletException{
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        try{
            et.begin();
            logger.info("store data in XML:"+filename);
            make_xml(filename,em);
            logger.info("remove data from DB");
            remove_staff_data_from_db(em);
            logger.info("after remove data finished");
            et.commit();
        }catch(Exception ex){
            et.rollback();
            throw new ServletException(ex);
        }
        finally{
            em.close();
        }
    }

    private void remove_staff_data_from_db(EntityManager em){
        em.createQuery("delete from  StaffEntity s where s.id<>-1 ").executeUpdate();
    }

    private void make_xml(String filename, EntityManager em) throws ServletException {
        try {
            Query q = em.createQuery(
                    "select staff " +
                            "from StaffEntity staff " +
                            "order by staff.id desc");
            StaffEntitiesList sl = new StaffEntitiesList();
            sl.setStaff_list(q.getResultList());

            JAXBContext jc = JAXBContext.newInstance(sl.getClass());
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            Path path = Paths.get(filename);
            Files.createDirectories(path.getParent());
            m.marshal(sl, path.toFile());
        }catch(Exception ex){
            throw new ServletException("could not marchal staff list into file");
        }
    }

}

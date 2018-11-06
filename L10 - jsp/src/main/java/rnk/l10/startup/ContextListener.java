package rnk.l10.startup;

import org.apache.log4j.Logger;
import rnk.l10.entities.xml.StaffEntitiesList;

import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
      try{
          ServletContext context=sce.getServletContext();
          Object o=context.getAttribute("startup");
          if ((o!=null) && (o instanceof Startup)){
              Startup startup=(Startup)o;
              if (startup.getInitialized()==1){
                  make_xml(get_filename(context));
              }
          }
      }catch(Exception ex){
          logger.error("Error while savind DB state to XML:", ex);
      }

    }

    private String get_filename(ServletContext context){
        return context.getAttribute(ServletContext.TEMPDIR)+"/xml/employees.xml";
    }

    private void make_xml(String filename) throws ServletException {
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        try{
            et.begin();
            Query q = em.createQuery(
                    "select staff "+
                            "from StaffEntity staff "+
                            "order by staff.id desc");
            StaffEntitiesList sl=new StaffEntitiesList();
            sl.setStaff_list(q.getResultList());

            JAXBContext jc=JAXBContext.newInstance(sl.getClass());
            Marshaller m=jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            Path path= Paths.get(filename);
            Files.createDirectories(path.getParent());
            m.marshal(sl, path.toFile());

            et.commit();
        }catch(Exception ex){
            et.rollback();
            throw new ServletException(ex);
        }
        finally{
            em.close();
        }
    }

}

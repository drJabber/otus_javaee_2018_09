package rnk.l10.startup;

import org.apache.log4j.Logger;
import rnk.l10.entities.StaffEntity;
import rnk.l10.entities.xml.StaffEntitiesList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(loadOnStartup = 1)
public class StartupServlet extends HttpServlet {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(StartupServlet.class.getName());

    @Override
    public void init(ServletConfig config)  {
        ServletContext context=config.getServletContext();
        try{
            if (context.getInitParameter("restore-at-startup").equals("Y")) {
                logger.info("before reading startup data");
                restore_staff_data_and_save_to_DB(get_filename(context));
            }
            context.setAttribute("startup", new Startup(1, 0));
        }catch(Exception ex) {
            logger.error("Error while restoring DB state from XML:", ex);
        }
    }

        private String get_filename(ServletContext context) throws NamingException {
            return context.getInitParameter("rnk-storage-path")+"/staff.xml";
        }

        private StaffEntitiesList restore_staff_data(String filename) throws JAXBException, IOException{
            JAXBContext jc=JAXBContext.newInstance(StaffEntitiesList.class);
            Unmarshaller m=jc.createUnmarshaller();

            Path xml_path= Paths.get(filename);
            if (xml_path.toFile().exists()){
                Files.createDirectories(xml_path.getParent());
                return (StaffEntitiesList)m.unmarshal(xml_path.toFile());
            }else{
                return null;
            }

        }

        private void save_data_to_db(StaffEntitiesList sl, EntityManager em){
            if (sl.getStaff_list()!=null){
                for (StaffEntity staff: sl.getStaff_list()) {
                    if (staff.getId()>=0){
                        em.merge(staff);
                    }
                }
            }
        }

        private void restore_staff_data_and_save_to_DB(String filename) throws ServletException{
            EntityManager em=emf.createEntityManager();
            EntityTransaction et=em.getTransaction();
            try{
                et.begin();

                StaffEntitiesList sl=restore_staff_data(filename);
                if (sl!=null){
                    logger.info("after restore from xml");
                    save_data_to_db(sl,em);
                    logger.info("after save to DB");
                }else{
                    logger.info("no saved staff");
                }
                et.commit();
            }catch(Exception ex){
                et.rollback();;
                throw new ServletException("error while restore data",ex);
            }finally {
                em.close();
            }

        }
}

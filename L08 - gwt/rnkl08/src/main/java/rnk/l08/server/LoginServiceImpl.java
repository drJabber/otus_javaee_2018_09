package rnk.l08.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.log4j.Logger;
import rnk.l08.client.LoginService;
import rnk.l08.shared.GwtServiceException;
import rnk.l08.shared.dto.User;
import rnk.l08.shared.validation.ValidationRule;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat

    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

    @Override
    public User authorize_from_session() throws GwtServiceException{

        return get_user_from_session();
    };

    @Override
    public Void logout() throws GwtServiceException {
        EntityManager em = emf.createEntityManager(); 
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("logout")
                    .registerStoredProcedureParameter(1,String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2,String.class, ParameterMode.IN);

            q.setParameter(1,user.getLogin());
            q.setParameter(2,user.getSession());
            q.execute();

            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
            logger.error("logout error:",e);
        }
        finally {
            em.close();
            remove_user_from_session();
        }
        
        return null;
    };

    @Override
    public User get_user_from_session() throws GwtServiceException {
        User user=null;
        HttpServletRequest rq=getThreadLocalRequest();
        HttpSession session=rq.getSession();
        Object u=session.getAttribute("user");
        if (u!=null && u instanceof User){
            user=(User)u;
        }
        return user;
    };

    @Override
    public Void save_user_in_session(User user) throws GwtServiceException{
        HttpServletRequest rq=getThreadLocalRequest();
        HttpSession session=rq.getSession();
        session.setAttribute("user", user);
        return null;
    };

    @Override
    public Void remove_user_from_session() throws GwtServiceException{
        HttpServletRequest rq=getThreadLocalRequest();
        HttpSession session=rq.getSession();
        session.removeAttribute("user");
        return null;
    };

    @Override
    public User authorize(User user) throws GwtServiceException {
        if (ValidationRule.isValid(user)){
            EntityManager em = emf.createEntityManager(); 
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();

                StoredProcedureQuery q = em
                        .createStoredProcedureQuery("authorize")
                        .registerStoredProcedureParameter(1,String.class, ParameterMode.IN)
                        .registerStoredProcedureParameter(2,String.class, ParameterMode.IN)
                        .registerStoredProcedureParameter(0,Integer.class, ParameterMode.OUT);

                q.setParameter(1,user.getLogin());
                q.setParameter(2,user.getPassword());
                q.execute();

                Object result=q.getOutputParameterValue(0);
                transaction.commit();

                if (result==null){
                    throw new Exception("ошибка входа");
                    return user;
                }else
                {
                    save_user_in_session(user);
                    return user;
                }
            }
            catch (Exception e){
                transaction.rollback();
                logger.error("login error:",e);
                throw new GwtServiceException("ошибка входа");
            }
            finally {
                em.close();
            }
        }else
        {
            throw new GwtServiceException("ошибка входа");
        }
    }
}
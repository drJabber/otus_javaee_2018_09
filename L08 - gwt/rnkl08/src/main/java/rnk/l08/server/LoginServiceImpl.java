package rnk.l08.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.log4j.Logger;
import rnk.l08.client.LoginService;
import rnk.l08.entities.HashedPasswordEntity;
import rnk.l08.shared.GwtServiceException;
import rnk.l08.shared.dto.HashedPasswordDTO;
import rnk.l08.shared.dto.SessionInfo;
import rnk.l08.shared.dto.User;
import rnk.l08.shared.validation.ValidationRule;
import rnk.l08.utils.PasswordHelper;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat

    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

    @Override
    public User authorize_from_session() throws GwtServiceException{

        return get_user_from_session();
    };

    @Override
    public void logout() throws GwtServiceException {
        EntityManager em = emf.createEntityManager(); 
        EntityTransaction transaction = em.getTransaction();
        try {
            User user=null;
            HttpServletRequest rq=getThreadLocalRequest();
            HttpSession session=rq.getSession();
            Object u=session.getAttribute("user");
            if (u instanceof User){
                user=(User)u;

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
        }
        catch (Exception e){
            transaction.rollback();
            logger.error("logout error:",e);
        }
        finally {
            em.close();
            remove_user_from_session();
        }
        
        return ;
    };


    public SessionInfo get_user_from_session(String session ) throws GwtServiceException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            StoredProcedureQuery q = em
                    .createStoredProcedureQuery("session_is_valid")
                    .registerStoredProcedureParameter("p_session_id",String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("o_valid", Integer.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter("o_expire", Timestamp.class, ParameterMode.OUT);

            q.setParameter("p_session_id",session);
            q.execute();

            Object valid=q.getOutputParameterValue("o_valid");
            Object expire=q.getOutputParameterValue("o_expire");
            transaction.commit();

            SessionInfo si=new SessionInfo();
            si.setIsValid((Integer)valid);
            si.setExpire((Timestamp)expire);
            return si;
        }catch (Exception e){

            transaction.rollback();
            logger.error("login error:",e);
            throw new GwtServiceException("ошибка входа");
        }
        finally {
            em.close();
        }

    }

    @Override
    public User get_user_from_session() throws GwtServiceException {
        User user=null;
        HttpServletRequest rq=getThreadLocalRequest();
        HttpSession session=rq.getSession(false);
        if (session!=null && !session.isNew()){
            Object u=session.getAttribute("user");
            if (u!=null && u instanceof User){
                user=(User)u;

                SessionInfo si=get_user_from_session(user.getSession());

                if (si.getIsValid()==null || si.getIsValid()!=1){
                    return null;
                }else
                {
                    user.setExpires(si.getExpire());
                    save_user_in_session(user);
                    return user;
                }
            }
        }
        return user;
    };

    @Override
    public void save_user_in_session(User user) throws GwtServiceException{
        HttpServletRequest rq=getThreadLocalRequest();
        HttpSession session=rq.getSession();
        session.setAttribute("user", user);
        return;
    };

    @Override
    public void remove_user_from_session() throws GwtServiceException{
        HttpServletRequest rq=getThreadLocalRequest();
        HttpSession session=rq.getSession();
        session.removeAttribute("user");
        return ;
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
                        .registerStoredProcedureParameter("p_login",String.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("p_password",String.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("o_session_id",String.class, ParameterMode.OUT)
                        .registerStoredProcedureParameter("o_expires", Timestamp.class, ParameterMode.OUT);

                q.setParameter("p_login",user.getLogin());
                q.setParameter("p_password",user.getPassword());
                q.execute();

                Object result=q.getOutputParameterValue("o_session_id");
                Object expires=q.getOutputParameterValue("o_expires");
                transaction.commit();

                if (result==null || ((String)result).isEmpty()){
                    throw new Exception("ошибка входа");
                }else
                {
                    user.setSession((String)result);
                    user.setExpires((Date)expires);
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

    @Override
    public HashedPasswordDTO hash_password(String password) throws GwtServiceException{
        try{
            PasswordHelper helper=new PasswordHelper();
            HashedPasswordEntity hp= helper.hashPassword(password);
            return new HashedPasswordDTO(hp.getPasswdhash(),hp.getPasswdsalt());
        }catch(Exception ex){
            throw new GwtServiceException("ошибка получения данных");
        }
    }
}
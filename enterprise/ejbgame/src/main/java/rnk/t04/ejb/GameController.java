package rnk.t04.ejb;

import rnk.t04.entities.AttemptEntity;
import rnk.t04.entities.UserEntity;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Singleton
@Startup
public class GameController{
    @PersistenceContext(unitName = "GAME_PU")
    private EntityManager em;

    @Resource
    TimerService timerservice;


    @PostConstruct
    void startup(){
        resumeAllUsers();
    }

    public UserEntity findUser(String login){
        Query q=em.createQuery("select u from UserEntity u  where u.login=:login").setParameter("login",login);
        List<UserEntity> list=q.getResultList();
        if (list.size()>0){
            return em.find(UserEntity.class,list.get(0).getId());
        }else{
            return null;
        }
    }


    public void saveUserAttempt(String login, UserAttempt attempt){
        UserEntity user=this.findUser(login);

        if (user==null){
            user=new UserEntity();
            user.setLogin(login);
            user.setSuspended(false);
            em.persist(user);
        }

        if (attempt.getId() == null){
            AttemptEntity a=new AttemptEntity();
            a.setUser(user);
            a.setNumber(attempt.getAttemptNumber());
            a.setResult(attempt.getResult());
            a.setSecret(attempt.getSecret());

            user.getAttempts().add(a);

            em.persist(a);

            em.merge(user);
        }else{
            AttemptEntity a=em.find(AttemptEntity.class, attempt.getId());
            a.setNumber(attempt.getAttemptNumber());
            a.setResult(attempt.getResult());           
  
            em.merge(a);
        }
    }

    public void suspendUserIfLastAttemptFailed(String login, UserAttempt attempt){
        Integer attemptNumber=attempt.getAttemptNumber();
        if (((attemptNumber==null) || (attemptNumber==0))&&(attempt.getResult()==false)){
            UserEntity user=this.findUser(login);
            if (user!=null){
                user.setSuspended(true);
                em.merge(user);

                TimerDto dto=new TimerDto(login);
                TimerConfig config=new TimerConfig(dto,true);

                timerservice.createSingleActionTimer(1*60*1000, config);
            }
        }
    }

    public void resumeUser(String login){
        UserEntity user=this.findUser(login);
        if (user!=null){
            user.setSuspended(false);
            em.merge(user);
        }
    }

    public void resumeAllUsers(){
        em.createQuery("update UserEntity set suspended=false").executeUpdate();
    }

    @Timeout
    private void timerExpired(Timer timer){
        TimerDto dto=(TimerDto)timer.getInfo();
        resumeUser(dto.getLogin());
    }

}
package rnk.t04.ejb;

@Singleton
public class GameController{
    @PersistenceContext(unitName = "GAME_PU")
    private EntityManager em;

    public UserEntity findUser(String login){
        Query q=em.createQuery("select e from UserEntity u where u.login=:login").setParameter("login",login);
        return q.getSingleResult();
    }

    public void saveUserAttempt(String login, UserAttempt attempt){
        UserEntity q=em.createQuery("select u from UserEntity u where u.login=:login").setParameter("login",login);
        UserEntity user=q.getSingleResult();

        if (q.getSingleResult()==null){
            user=new UserEntity();
            user.setLogin(login);
            em.persist(user);
        }

        if (attempt.getId() == null){
            AttemptEntity a=new AttemptEntity();
            a.setUser(user);
            a.setNumber(attempt.getAttemptNumber());
            a.setResult(attempt.getResult());
            a.setSecret(attempt.getSecret())

            em.persist(a);
        }else{
            AttemptEntity a=em.find(AttemptEntity.class, attempt.getId());
            a.setNumber(attempt.getAttemptNumber());
            a.setResult(attempt.getResult());           
  
            em.persist(a);
        }
    }




}
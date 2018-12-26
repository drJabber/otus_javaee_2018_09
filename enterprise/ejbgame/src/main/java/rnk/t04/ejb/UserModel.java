package rnk.t04.ejb;


import rnk.t04.entities.AttemptEntity;
import rnk.t04.entities.UserEntity;
import rnk.t04.rest.model.UserData;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.registry.infomodel.User;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Stateful(name="UserModel")
public class UserModel implements IUserModel{
    private String currentUserName;
    private UserAttempt currentAttempt=new UserAttempt();
    private UserAttempt lastAttempt=null;
    private List<UserAttempt> attempts;

    @EJB
    RandomGenerator random;

    @EJB
    GameController controller;

    @Override
    public void initialize(UserData userData) {
        UserEntity entity=controller.getEntity(userData.getLogin());
        attempts=populateAttempts(entity.getAttempts());
        currentUserName=userData.getLogin();
        currentAttempt=processAttempt(userData.getValue());
    }

    @Override
    public String getCurrentUserName() {
        return currentUserName;
    }

    @Override
    public UserAttempt getCurrentAttempt() {
        return currentAttempt;
    }

    @Override
    public List<UserAttempt> getAttempts() {
        return attempts;
    }

    private List<UserAttempt> populateAttempts(List<AttemptEntity> attempts) {
        List<UserAttempt> list=new ArrayList<>();
        attempts.forEach(a->list.add(new UserAttempt(a.getId(),a.getNumber(),a.getResult(),a.getSecret())));
        return list;
    }

    private UserAttempt processAttempt(Integer guessValue){
        UserAttempt attempt=null;
        Integer newNumber=0;
        Boolean guessResult=false;

        if (attempts.size()>0){
            lastAttempt=attempts.get(attempts.size()-1);

            newNumber=(lastAttempt.getAttemptNumber()+1)%3;
            guessResult=lastAttempt.getResult();
        }else{
            attempt=new UserAttempt();
            attempt.setAttemptNumber(0);
            attempt.setSecret(computeSecret());
        }

        Boolean isNew=(newNumber==0)||(guessResult==true);
        if (isNew){
            attempt=new UserAttempt();
            attempts.add(attempt);
        }else{
            attempt=lastAttempt;
        }
        attempt.setAttemptNumber(newNumber);
        attempt.setResult(attempt.getSecret().equals(guessValue))

        controller.saveUserAttempt(currentUserName, attempt);
        
        return attempt;
    }

    private Integer computeSecret(){
        return  (int)Math.round(Math.floor(random.getNext()*10));
    }

}

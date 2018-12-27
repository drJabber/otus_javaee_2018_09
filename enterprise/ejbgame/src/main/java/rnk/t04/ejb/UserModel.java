package rnk.t04.ejb;


import rnk.t04.entities.AttemptEntity;
import rnk.t04.entities.UserEntity;
import rnk.t04.rest.model.UserData;

import javax.ejb.*;
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
    private Boolean suspended;
    private Integer currentGuess;
    private UserAttempt currentAttempt=new UserAttempt();
    private UserAttempt lastAttempt=null;
    private List<UserAttempt> attempts;

    @EJB
    RandomGenerator random;

    @EJB
    GameController controller;

    @EJB
    TimerService timerservice;

    @Override
    public void initialize(UserData userData) {
        UserEntity entity=controller.findUser(userData.getLogin());

        currentUserName=userData.getLogin();
        currentGuess=userData.getValue();

        if (!entity.getSuspended()){
            suspended=false;
            attempts=populateAttempts(entity);
            currentAttempt=processAttempt(userData.getValue());
            suspendIfLastAttemptFailed(currentUserName,currentAttempt);
        }else{
            suspended=true;
        }
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

    @Override
    public Integer getCurrentGuess(){
        return currentGuess;
    }

    @Override
    public String getCheckResultText(){
        if (currentAttempt!=null){
            if (currentAttempt.getResult()){
                return "You WIN!";
            }else{
                return "Heh... looser...";
            }
        } else{
            return "Not checked yet";
        }
    }

    @Override
    public String getSuspendedText(){
        if (suspended){
            return "This login is suspended for some time, please try again later";
        }else{
            return "";
        }
    }
        
    
    private List<UserAttempt> populateAttempts(UserEntity entity) {
        List<UserAttempt> list=new ArrayList<>();
        if (entity!=null){
            entity.getAttempts().forEach(a->list.add(new UserAttempt(a.getId(),a.getNumber(),a.getResult(),a.getSecret())));
        }
        return list;
    }

    private UserAttempt processAttempt(Integer guessValue){
        UserAttempt attempt=null;
        Integer newNumber=1;
        Boolean guessResult=false;

        if (attempts.size()>0){
            lastAttempt=attempts.get(attempts.size()-1);

            newNumber=(lastAttempt.getAttemptNumber()+1)%3;
            guessResult=lastAttempt.getResult();
        }

        Boolean isNew=(lastAttempt==null)||(newNumber==0)||(guessResult==true);
        if (isNew){
            attempt=new UserAttempt();
            attempt.setSecret(computeSecret());
            attempts.add(attempt);
        }else{
            attempt=lastAttempt;
        }
        attempt.setAttemptNumber(newNumber);
        attempt.setResult(attempt.getSecret().equals(guessValue));

        controller.saveUserAttempt(currentUserName, attempt);

        if (attempt.getResult()==true){
            attempt.setAttemptNumber(null);
        }

        return attempt;
    }

    private Integer computeSecret(){
        return  (int)Math.round(Math.floor(random.getNext()*10));
    }

    private void suspendIfLastAttemptFailed(String login, UserAttempt attempt){
        controller.suspendUserIfLastAttemptFailed(login,attempt);
        timerservice.createSingleActionTimer(1*60*1000, new TimerConfig(new TimerDto(login),false));
    }

    @Timeout
    private void timerExpired(Timer timer){
        TimerDto dto=(TimerDto)timer.getInfo();
        controller.resumeUser(dto.getLogin());
    }
    
}

package rnk.t04.ejb;

import rnk.t04.rest.model.UserData;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IUserModel {
    void initialize(UserData userData);
    String getCurrentUserName();
    UserAttempt getCurrentAttempt();
    List<UserAttempt> getAttempts();
}

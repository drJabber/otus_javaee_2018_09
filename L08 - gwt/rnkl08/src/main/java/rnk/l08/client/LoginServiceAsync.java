package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import rnk.l08.shared.GwtServiceException;
import rnk.l08.shared.dto.HashedPasswordDTO;
import rnk.l08.shared.dto.User;

public interface LoginServiceAsync {
    void authorize(User user, AsyncCallback<User> async) throws GwtServiceException;
    void authorize_from_session(AsyncCallback<User> async) throws GwtServiceException;
    void logout(AsyncCallback<Void> async) throws GwtServiceException;
    void get_user_from_session(AsyncCallback<User> async) throws GwtServiceException;
    void save_user_in_session(User user, AsyncCallback<Void> async) throws GwtServiceException;
    void remove_user_from_session(AsyncCallback<Void> async) throws GwtServiceException;
    void hash_password(String password, AsyncCallback<HashedPasswordDTO> async);
}

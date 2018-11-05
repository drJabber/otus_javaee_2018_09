package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import rnk.l08.shared.GwtServiceException;
import rnk.l08.shared.dto.HashedPasswordDTO;
import rnk.l08.shared.dto.User;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {
    User authorize(User user) throws GwtServiceException;
    User authorize_from_session() throws GwtServiceException;
    void logout() throws GwtServiceException;
    User get_user_from_session() throws GwtServiceException;
    void save_user_in_session(User user) throws GwtServiceException;
    void remove_user_from_session() throws GwtServiceException;
    HashedPasswordDTO hash_password(String password) throws GwtServiceException;
}

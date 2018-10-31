package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import rnk.l08.shared.GwtServiceException;
import rnk.l08.shared.dto.User;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {
    User authorize(User user) throws GwtServiceException;
    User authorize_from_session() throws GwtServiceException;
    Void logout() throws GwtServiceException;
    User get_user_from_session() throws GwtServiceException;
    Void save_user_in_session(User user) throws GwtServiceException;
    Void remove_user_from_session() throws GwtServiceException;

    /**
     * Utility/Convenience class.
     * Use LoginService.App.getInstance() to access static instance of LoginServiceAsync
     */
    public static class App {
        private static final LoginServiceAsync ourInstance = (LoginServiceAsync) GWT.create(LoginService.class);

        public static LoginServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

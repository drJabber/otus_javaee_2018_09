package rnk.l08.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

@RemoteServiceRelativePath("Service")
public interface Service extends RemoteService {

    String getCurrencies();
    String getNews();
    /**
     * Utility/Convenience class.
     * Use Service.App.getInstance() to access static instance of ServiceAsync
     */
    class App {
        private static final ServiceAsync ourInstance = (ServiceAsync) GWT.create(Service.class);

        public static ServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

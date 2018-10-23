package rnk.l08.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

@RemoteServiceRelativePath("GwtRS")
public interface GwtRS extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use GwtRS.App.getInstance() to access static instance of GwtRSAsync
     */
    public static class App {
        private static final GwtRSAsync ourInstance = (GwtRSAsync) GWT.create(GwtRS.class);

        public static GwtRSAsync getInstance() {
            return ourInstance;
        }
    }
}

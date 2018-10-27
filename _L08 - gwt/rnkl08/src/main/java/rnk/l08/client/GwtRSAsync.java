package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GwtRSAsync {
    void search(String query, AsyncCallback<Void> async);
}

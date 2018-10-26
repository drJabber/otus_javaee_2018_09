package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync {
    public void getCurrencies(AsyncCallback<String> async);
}

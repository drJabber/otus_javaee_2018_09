package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync {
    void getCurrencies(AsyncCallback<String> async);

    void getNews(AsyncCallback<String> async);
}

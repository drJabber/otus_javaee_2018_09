package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import rnk.l08.shared.GwtServiceException;

import javax.servlet.ServletException;

public interface ServiceAsync {
    void getCurrencies(AsyncCallback<String> async) throws GwtServiceException;

    void getNews(AsyncCallback<String> async);
}

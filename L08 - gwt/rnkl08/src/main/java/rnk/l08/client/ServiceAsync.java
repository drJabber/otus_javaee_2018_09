package rnk.l08.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import rnk.l08.shared.dto.User;
import rnk.l08.shared.GwtServiceException;

public interface ServiceAsync {
    void getCurrencies(AsyncCallback<String> async) throws GwtServiceException;

    void getNews(AsyncCallback<String> async) throws GwtServiceException;

    void authorize(User user, AsyncCallback<Integer> async) throws GwtServiceException;
}

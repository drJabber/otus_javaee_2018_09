package rnk.l08.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import rnk.l08.client.Service;

public class ServiceImpl extends RemoteServiceServlet implements Service {
    @Override
    public String getCurrencies() {
        return "Hello from server!";
    }
}
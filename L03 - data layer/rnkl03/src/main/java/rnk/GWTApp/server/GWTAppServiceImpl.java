package rnk.GWTApp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import rnk.GWTApp.client.GWTAppService;

public class GWTAppServiceImpl extends RemoteServiceServlet implements GWTAppService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
      return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}
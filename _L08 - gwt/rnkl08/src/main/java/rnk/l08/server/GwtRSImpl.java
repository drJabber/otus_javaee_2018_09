package rnk.l08.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import rnk.l08.client.GwtRS;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GwtRSImpl extends RemoteServiceServlet implements GwtRS {
    @Override
    public void search(String query) throws ServletException {
//        try
//        {
//            HttpServletResponse rsp=getThreadLocalResponse();
//            rsp.sendRedirect("http://google.com/search");
//        }catch (Exception ex){
//            throw new ServletException("search fails");
//        }
    }
}
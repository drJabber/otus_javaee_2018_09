package rnk.l10.startup;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1)
public class StartupServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute("startup",new Startup(1,0));
    }
}

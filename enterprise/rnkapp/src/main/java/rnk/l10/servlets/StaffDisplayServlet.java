package rnk.l10.servlets;


import rnk.l10.entities.beans.StaffDisplayBean;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/main/admin/staff")
public class StaffDisplayServlet extends HttpServlet {

    @EJB
    StaffDisplayBean staff;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
	    req.getSession().setAttribute("staff",staff);
        RequestDispatcher dispatcher=req.getRequestDispatcher("/main/admin/staff2");
        dispatcher.forward(req, resp);
    }
}

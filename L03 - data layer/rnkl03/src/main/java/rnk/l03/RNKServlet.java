package rnk.l03;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rnk.l03.utils.Loader;


@WebServlet("/rnk_jdbc")
public class RNKServlet extends HttpServlet {

    @Resource(name="jdbc/rnk_db")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try(
            Connection cn=ds.getConnection();
            PreparedStatement q=cn.prepareStatement(
                    "select s.fio, p.position from staff s " +
                            "join positions p on p.id=s.position_id");
            ResultSet rs=q.executeQuery())
        {    PrintWriter pw=rsp.getWriter();
            StringBuilder sb=new StringBuilder();
            while (rs.next())
            {
                sb.append(
                        Stream.of(
                                rs.getString("fio")+" "+
                                rs.getString("position")

                        ).collect(Collectors.joining("|"))
                );
            }

            pw.println(sb.toString());


        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try(
                Connection cn=ds.getConnection();
                PreparedStatement q=cn.prepareStatement(
                        "insert into authorities(id, authority) values(?,?)");
                )
        {
            Loader loader=new Loader();
            loader.loadAll();
            ArrayList<String> authorities=loader.getAuthorities();
            int idx=1;
            for (String authority: authorities
                 ) {
                q.setString(2,authority);
                q.setInt(1,idx);
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("Authority '"+authority+"' created");
            }

        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try(
                Connection cn=ds.getConnection();
                PreparedStatement q=cn.prepareStatement(
                        "update authorities set authority=? where id=?");
        )
        {
            q.setString(1,"can_get_general_info");
            q.setInt(2,1);
            q.executeUpdate();

            q.setString(1,"can_modify_general_info");
            q.setInt(2,4);
            q.executeUpdate();
        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try(
                Connection cn=ds.getConnection();
                PreparedStatement q=cn.prepareStatement(
                        "delete from authorities where id=?");
        )
        {
            q.setInt(1,6);
            q.executeUpdate();
        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
    }
}

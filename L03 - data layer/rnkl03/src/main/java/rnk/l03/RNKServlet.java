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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rnk.l03.utils.Loader;


@WebServlet("/rnk_jdbc")
public class RNKServlet extends HttpServlet {


    @Resource(name="jdbc/rnk_db")
    private DataSource ds;

    private void saveAuthorities(Connection conn, HttpServletResponse rsp, Loader loader) throws ServletException,IOException {
        try(
                PreparedStatement q=conn.prepareStatement(
                        "insert into authorities(id, authority) values(?,?)");
        )
        {
            List<String> authorities=loader.getAuthorities();
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

    private void saveRoles(Connection conn, HttpServletResponse rsp, Loader loader) throws ServletException,IOException {
        try(
                PreparedStatement q=conn.prepareStatement(
                        "insert into roles(role) values(?)");
        )
        {
            List<String> roles=loader.getRoles();
            int idx=1;
            for (String role: roles
            ) {
                q.setString(1,role);
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("Role '"+role+"' created");
            }

        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }

    }

    private void saveRoleAuth(Connection conn, HttpServletResponse rsp, Loader loader) throws ServletException,IOException {
        try(
                PreparedStatement q=conn.prepareStatement(
                        "insert into role_auth(role_id,auth_id) values(?,?)");
        )
        {
            List<String []> role_auths=loader.getRole_auth();
            int idx=1;
            for (String[] role_auth: role_auths
            ) {
                q.setInt(1, Integer.parseInt(role_auth[0]));
                q.setInt(2, Integer.parseInt(role_auth[1]));
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("Role_auth '"+role_auth[0]+" "+role_auth[1]+"' created");
            }

        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }

    }

    private void savePositions(Connection conn, HttpServletResponse rsp, Loader loader) throws ServletException,IOException {
        try(
                PreparedStatement q=conn.prepareStatement(
                        "insert into positions(position, default_dept_id, head_id, default_role_id, default_salary) values(?,?,?,?,?)");
        )
        {
            List<String []> positions=loader.getPositions();
            int idx=1;
            for (String[] position: positions
            ) {
                q.setString(1, position[0]);
                q.setInt(2, Integer.parseInt(position[1]));

                String p=position[2];
                if (p.isEmpty()){
                    q.setNull(3, Types.INTEGER);
                }else{
                    q.setInt(3, Integer.parseInt(p));
                }
                q.setInt(4, Integer.parseInt(position[3]));
                q.setInt(5, Integer.parseInt(position[4]));
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("position '"+position[0]+"' created");
            }

        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }

    }

    private void saveDepartaments(Connection conn, HttpServletResponse rsp, Loader loader) throws ServletException,IOException {
        try(
                PreparedStatement q=conn.prepareStatement(
                        "insert into departaments(departament,head_dept_id,head_of_dept_id,town) values(?,?,?,?)");
        )
        {
            List<String []> departaments=loader.getDepartaments();
            int idx=1;
            for (String[] departament: departaments
            ) {
                q.setString(1, departament[0]);

                String p=departament[1];
                if (p.isEmpty()){
                    q.setNull(2, Types.INTEGER);
                }else{
                    q.setInt(2, Integer.parseInt(p));
                }

                p=departament[2];
                if (p.isEmpty()){
                    q.setNull(3, Types.INTEGER);
                }else{
                    q.setInt(3, Integer.parseInt(p));
                }

                q.setString(4, departament[3]);
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("departament '"+departament[0]+"' created");
            }

        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }

    }

    private void saveStaff(Connection conn, HttpServletResponse rsp, Loader loader) throws ServletException,IOException {
        try(
                PreparedStatement q=conn.prepareStatement(
                        "insert into staff(fio,position_id,departament_id,salary,role_id,login,passwd_hash,passwd_salt) values(?,?,?,?,?,?,?,?)");
        )
        {
            List<String []> staff=loader.getStaff();
            int idx=1;
            for (String[] person: staff
            ) {
                q.setString(1, person[0]);
                q.setInt(2, Integer.parseInt(person[1]));
                q.setInt(3, Integer.parseInt(person[2]));
                q.setInt(4, Integer.parseInt(person[3]));
                q.setInt(5, Integer.parseInt(person[4]));
                q.setString(6, person[5]);
                q.setString(7, person[6]);
                q.setString(8, person[7]);
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("person '"+person[0]+"' created");
            }

        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        Map<String,String[]> parameters=rq.getParameterMap();
        if (parameters.containsKey("get_max_salary_fio")) {
            try(
                    Connection cn=ds.getConnection();
                    CallableStatement q=cn.prepareCall("{?=call get_max_salary_fio()}");
                    )
            {
                q.registerOutParameter(1,Types.VARCHAR  );
                q.execute();

                rsp.setCharacterEncoding("utf-8");
                rsp.getWriter().println(q.getString(1).split(" ")[0]);


            }catch(SQLException ex)
            {
                throw new ServletException(ex);
            }
        }else
        {
            try(
                    Connection cn=ds.getConnection();
                    PreparedStatement q=cn.prepareStatement(
                            "select s.id, s.fio, p.position, d.departament from staff s " +
                                    "join positions p on p.id=s.position_id "+
                                    "join departaments d on d.id=s.departament_id "+
                                    "order by s.id desc");
                    ResultSet rs=q.executeQuery())
            {
                StringBuilder sb=new StringBuilder();
                while (rs.next())
                {
                    sb.append(
                            Stream.of(
                                    rs.getString("id")+" - "+
                                            rs.getString("fio")+": "+
                                            rs.getString("position")+" at "+
                                            rs.getString("departament")+"\n"

                            ).collect(Collectors.joining("|"))
                    );
                }

                rsp.setCharacterEncoding("utf-8");
                rsp.getWriter().println(sb.toString());


            }catch(SQLException ex)
            {
                throw new ServletException(ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {

        try(Connection cn=ds.getConnection()){
            Loader loader=new Loader();
            loader.loadAll();

            saveAuthorities(cn,rsp, loader);
            saveRoles(cn,rsp,loader);
            saveRoleAuth(cn,rsp,loader);
            saveDepartaments(cn,rsp,loader);
            savePositions(cn,rsp,loader);
            saveStaff(cn,rsp,loader);
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
                        "update staff set fio=?, position_id=? where id=?");
        )
        {
            q.setString(1,"Марзаганов Зульпикар Зульпикарович");
            q.setInt(2,3);
            q.setInt(3,4);
            q.executeUpdate();

            q.setString(1,"Петрова Марина Сергеевна");
            q.setInt(2,4);
            q.setInt(3,5);
            q.executeUpdate();
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
                        "delete from staff where id=?");
        )
        {
            q.setInt(1,6);
            q.executeUpdate();
            q.setInt(1,7);
            q.executeUpdate();
        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
    }
}

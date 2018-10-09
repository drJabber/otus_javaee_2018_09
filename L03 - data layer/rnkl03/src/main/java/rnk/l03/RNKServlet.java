package rnk.l03;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rnk.l03.jpa_entities.*;
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
            List<AuthorityEntity> authorities=loader.getAuthorities();
            int idx=1;
            for (AuthorityEntity authority: authorities
            ) {
                q.setString(2,authority.getAuthority());
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
            List<RoleEntity> roles=loader.getRoles();
            int idx=1;
            for (RoleEntity role: roles
            ) {
                q.setString(1,role.getRole());
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
            List<RoleAuthPair> role_auths=loader.getRole_auth();
            int idx=1;
            for (RoleAuthPair role_auth: role_auths
            ) {
                q.setInt(1, role_auth.getFirst());
                q.setInt(2, role_auth.getSecond());
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("Role_auth '"+role_auth.getFirst()+" "+role_auth.getSecond()+"' created");
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
            List<PositionEntity> positions=loader.getPositions();
            int idx=1;
            for (PositionEntity position: positions
            ) {
                q.setString(1, position.getPosition());
                q.setInt(2, position.getDefault_dept_id());

                Integer p=position.getHead_id();
                if (p<0){
                    q.setNull(3, Types.INTEGER);
                }else{
                    q.setInt(3, p);
                }
                q.setInt(4, position.getDefault_role_id());
                q.setInt(5, position.getDefault_salary());
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("position '"+position.getPosition()+"' created");
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
            List<DepartamentEntity> departaments=loader.getDepartaments();
            int idx=1;
            for (DepartamentEntity departament: departaments
            ) {
                q.setString(1, departament.getDepartament());

                Integer h=departament.getHead_dept_id();
                if (h<0){
                    q.setNull(2,Types.INTEGER);
                }else
                {
                    q.setInt(2,h);
                }

                h=departament.getHead_of_dept_id();
                if (h<0){
                    q.setNull(3,Types.INTEGER);
                }else
                {
                    q.setInt(3,h);
                }

                q.setString(4, departament.getTown());
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("departament '"+departament.getDepartament()+"' created");
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
            List<StaffEntity> staff=loader.getStaff();
            int idx=1;
            for (StaffEntity person: staff
            ) {
                q.setString(1, person.getFio());
                q.setInt(2, person.getPosition_id());
                q.setInt(3, person.getDepartament_id());
                q.setInt(4, person.getSalary());
                q.setInt(5, person.getRole_id());
                q.setString(6, person.getLogin());
                q.setString(7, person.getPasswd_hash());
                q.setString(8, person.getPasswd_salt());
                q.executeUpdate();
                idx++;
                rsp.getWriter().println("person '"+person.getFio()+"' created");
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
            q.setInt(3,18);
            q.executeUpdate();

            q.setString(1,"Петрова Марина Сергеевна");
            q.setInt(2,4);
            q.setInt(3,19);
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
            q.setInt(1,20);
            q.executeUpdate();
            q.setInt(1,21);
            q.executeUpdate();
        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
    }
}

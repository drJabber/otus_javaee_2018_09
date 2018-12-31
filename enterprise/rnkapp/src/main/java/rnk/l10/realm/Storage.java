package rnk.l10.realm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Storage {

    private Connection conn;
    private final static Logger LOGGER = Logger.getLogger(Storage.class.getName());

    private final static String SALT_FOR_USER = "SELECT salt FROM public.staff u WHERE login = ?;";
    private final static String VERIFY_USER = "SELECT username FROM public.staff u WHERE login = ? AND passwd_hash = ?;";
    private final static String EXTRACT_GROUPS = "SELECT role FROM public.staff_roles r WHERE login = ?;";

    public Storage(String dataSource) {
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (javax.sql.DataSource) ctx.lookup(dataSource);
            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant create connection", e);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    LOGGER.log(Level.SEVERE, "Cant close context", e);
                }
            }
        }
    }

    public Storage(String user, String passwd) {
        try {
            Class.forName("org.postgresql.Driver").newInstance();

            conn = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/dbotus" + user + "&password=" + passwd + "");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting connection", ex);
        }
    }

    public String getSaltForLogin(String login) {
        String salt = null;
        try {
            PreparedStatement s = conn.prepareStatement(SALT_FOR_USER);
            s.setString(1, login);
            ResultSet rs = s.executeQuery();

            if (rs.next()) {
                salt = rs.getString(1);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "User not found", ex);
        }
        return salt;
    }

    public boolean validateUser(String login, String password) {

        try {
            PreparedStatement s = conn.prepareStatement(VERIFY_USER);
            s.setString(1, login);
            s.setString(2, password);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "User validation failed", ex);
        }
        return false;
    }

    public List<String> retrieveGroups(String login)    {
        String salt = null;
        try {
            PreparedStatement s = conn.prepareStatement(EXTRACT_GROUPS);
            List<String> l=new ArrayList<>();
            s.setString(1, login);
            ResultSet rs = s.executeQuery();
            while (rs.next()){
                l.add(rs.getString("role"));
            }
            return l;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "User not found", ex);
            return null;
        }
    }
}

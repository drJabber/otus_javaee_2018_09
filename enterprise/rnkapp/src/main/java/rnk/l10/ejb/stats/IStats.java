package rnk.l10.ejb.stats;

import javax.ejb.Remote;
import javax.servlet.ServletException;

//@Remote
public interface IStats {
    Integer store(String token, Object stats) throws ServletException;
    void toggle(String subject, String value) throws ServletException;
    String get(String subject) throws ServletException;
    String control(String service_password,String password, String command, String subject) throws  ServletException;
}

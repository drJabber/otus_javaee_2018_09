package rnk.l10.realm;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;

public class RnkRealm extends AppservRealm {

    private String jaasCtxName;
    private String dsName;

    @Override
    protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
        _logger.fine("init()");
        jaasCtxName = props.getProperty("jaas-context", "RnkRealm");
        dsName = props.getProperty("dataSource", "jdbc/rnk-jpa");
    }

    @Override
    public String getJAASContext() {
        return jaasCtxName;
    }

    @Override
    public String getAuthType() {
        return "RnkJdbcRealm";
    }

    public String[] authenticate(String login, String password) throws Exception {
        Storage storage = new Storage(dsName);

        // salt it
        String salt = store.salt(login);

        String[] result = null;

        if (salt != null) {
            HashUtils utils = new HashUtils();
            // get the byte[] from the salt
            byte[] saltBytes = utils.fromBase64(salt);

            // hash password and salt
            byte[] passwordBytes = utils.hash(password, saltBytes);

            // Base64 encode to String
            String encoded_passwd = utils.toBase64(passwordBytes);

            // validate it 
            if (storage.validateUser(login, encoded_passwd)) {
                result = storage.retrieveGroups(login);
            }
        }
        return result;
    }

    @Override
    public Enumeration getGroupNames(String login) throws InvalidOperationException, NoSuchUserException {
        //never called. Only here to make compiler happy.
        return null;
    }
}

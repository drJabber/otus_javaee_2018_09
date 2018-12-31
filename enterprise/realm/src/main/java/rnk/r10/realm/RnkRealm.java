package rnk.r10.realm;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;

import java.util.*;
import java.util.logging.Level;

public class RnkRealm extends AppservRealm {

    private String jaasCtxName;
    private String dsName;
    private Storage storage;

    @Override
    protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
        _logger.info("initialize rnk realm");
        jaasCtxName = props.getProperty("jaas-context", "RnkRealm");
        dsName = props.getProperty("dataSource", "jdbc/rnk-jpa");
        storage = new Storage(dsName);
    }

    @Override
    public String getJAASContext() {
        return jaasCtxName;
    }

    @Override
    public String getAuthType() {
        return "RnkJdbcRealm";
    }

    public Boolean authenticate(String login, String password) {
        _logger.info("authenticate user with rnk realm");

        Boolean result=false;

        // salt it
        String salt = storage.getSaltForLogin(login);



//        String[] result =null;

        if (salt != null) {
            HashUtils utils = new HashUtils();

//            byte[] bsalt=utils.salt(24);
//            _logger.info("generated salt");
//            String newSalt=utils.toBase64(bsalt);
//            _logger.info(newSalt);
//            byte[] bhash=utils.hash("blah",bsalt);
//            _logger.info("generated hash");
//            String newHash=utils.toBase64(bhash);
//            _logger.info(newHash);
//            _logger.info(String.format("salt:%s, passwd: %s", newSalt, newHash));


            // get the byte[] from the salt
            byte[] saltBytes = utils.fromBase64(salt);

            // hash password and salt
            byte[] passwordBytes = utils.hash(password, saltBytes);

            // Base64 encode to String
            String encoded_passwd = utils.toBase64(passwordBytes);
            _logger.info(String.format("user: %s, salt:%s, encoded_passwd: %s, encoded_passwd: %s", login,salt, password, encoded_passwd));
            return storage.validateUser(login,encoded_passwd);
//            // validate it
//            if (storage.validateUser(login, encoded_passwd)) {
//                result = storage.retrieveGroups(login).stream().toArray(String[]::new);
//            }
        }
        return result;
    }

    @Override
    public Enumeration getGroupNames(String login) throws InvalidOperationException, NoSuchUserException {
        _logger.info("rnk realm - get group names");
        return Collections.enumeration(storage.retrieveGroups(login));
    }
}

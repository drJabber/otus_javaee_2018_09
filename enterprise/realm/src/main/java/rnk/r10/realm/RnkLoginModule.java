package rnk.r10.realm;

import com.sun.appserv.security.AppservPasswordLoginModule;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.security.auth.login.LoginException;

public class RnkLoginModule extends AppservPasswordLoginModule {

    @Override
    protected void authenticateUser() throws LoginException {

        if (!(_currentRealm instanceof RnkRealm)) {
            throw new LoginException("Not supported realm, check login.conf");
        }
        RnkRealm rnkRealm = (RnkRealm) _currentRealm;


        if (rnkRealm.authenticate(_username, _password)) {
            throw new LoginException(String.format("Authenthication failed for user %s", _username));
        }
        // Get group names for the authenticated user from the Realm class
        Enumeration enumeration = null;
        try {
            enumeration = rnkRealm.getGroupNames(_username);
        } catch (InvalidOperationException e) {
            throw new LoginException("InvalidOperationException was thrown for getGroupNames() on RnkRealm");
        } catch (NoSuchUserException e) {
            throw new LoginException("NoSuchUserException was thrown for getGroupNames() on RnkRealm");
        }

        List<String> g = new ArrayList<String>();
        while (enumeration != null && enumeration.hasMoreElements()) {
            g.add((String) enumeration.nextElement());
        }

        String[] authenticatedGroups = g.toArray(new String[g.size()]);

        // Call commitUserAuthentication with the group names the user belongs to.
        // Note that this method is called after the authentication has succeeded.
        // If authentication failed do not call this method. Global instance field
        // succeeded is set to true by this method.
        commitUserAuthentication(authenticatedGroups);
    }

}
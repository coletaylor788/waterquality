package model;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Cole on 9/14/16.
 */
public class ApacheShiroAuthenticate implements Authenticate {

    private static final transient Logger log = LoggerFactory.getLogger(ApacheShiroAuthenticate.class);

    public ApacheShiroAuthenticate() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("file:src/model/shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

    public String getCurrentUsername() {
        Subject currentUser = SecurityUtils.getSubject();
        return getUsername(currentUser);
    }

    public String getUsername(Subject user) {
        Session session = user.getSession(true);

        SimplePrincipalCollection p = (SimplePrincipalCollection)
                session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (p != null) {
            return p.getPrimaryPrincipal().toString();
        } else {
            return null;
        }
    }

    public Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }

    public boolean isLoggedIn() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isAuthenticated();
    }

    public void login(String username, String password) {

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            currentUser.login(token);
        } else {
            throw new ConcurrentAccessException("User: " + username
                    + " is already authenticated. Cannot authenticate more than one user");
        }
    }

    public Subject logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return currentUser;
    }

}

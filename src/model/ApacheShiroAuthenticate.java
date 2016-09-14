package model;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Cole on 9/14/16.
 */
public class ApacheShiroAuthenticate {

    private static final transient Logger log = LoggerFactory.getLogger(ApacheShiroAuthenticate.class);

    public ApacheShiroAuthenticate() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("file:src/model/shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

    public String getCurentUsername() {
        return null;
    }

    public Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }

    public boolean isLoggedIn() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isAuthenticated();
    }

    /**
     * Logs a user in. This will set the user to the "logged-in" state
     *
     * @param username The username to login
     * @param password The password of that user
     * @throws UnknownAccountException will be thrown if username doesn't exist
     * @throws IncorrectCredentialsException will be thrown if username and password don't match
     * @throws AuthenticationException will be thrown if another execption occurs when logging in
     */
    public void login(String username, String password) throws
            UnknownAccountException, IncorrectCredentialsException, AuthenticationException {

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);

            currentUser.login(token);
        } else {
            //TODO something...
        }
    }

    public Subject logout() {
        return null;
    }

}

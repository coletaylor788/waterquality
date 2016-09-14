package model;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

/**
 * Interface used to authenticate users with the system.
 * Example Usage:
 *   ApacheShiroAuthenticate auth = new ApacheShiroAuthenticate();
 *   auth.login("user", "pass");
 *
 * @author Cole Taylor
 * @version 1.0
 */
public interface Authenticate {

    /**
     * @return true if a user is logged in, false otherwise.
     */
    boolean isLoggedIn();

    /**
     * Returns the currently logged in user
     *
     * @return the Subject (user) that is currently logged in.
     */
    Subject getCurrentUser();

    /**
     * Returns the current username
     *
     * @return a string representation of the currently logged in user's username
     */
    String getCurrentUsername();

    /**
     * Returns the string representation of Subject (user) passed in
     *
     * @param user is the subject to extract the username from
     * @return a string representation of the username of subject
     */
    String getUsername(Subject user);

    /**
     * Logs a user in. This will set the user to the "logged-in" state
     *
     * @param username The username to login
     * @param password The password of that user
     * @throws UnknownAccountException is thrown if the username doesn't exist
     * @throws IncorrectCredentialsException is thrown if the username and password don't match
     * @throws ConcurrentAccessException when the username is already authenticated
     * @throws AuthenticationException when another exception occurs when logging in
     */
    void login(String username, String password) throws UnknownAccountException,
            IncorrectCredentialsException, ConcurrentAccessException, AuthenticationException;

    /**
     * Logs out the user that is currently logged in. Since there can only
     * be one session (user logged in), it terminates the only session running
     *
     * @return the Subject or user object that was formerly logged in.
     */
    Subject logout();
}

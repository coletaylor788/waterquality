package model;

import org.apache.shiro.subject.Subject;

/**
 * Created by cole on 9/14/16.
 */
public interface Authenticate {
    boolean isLoggedIn();
    Subject getCurrentUser();
    String getCurrentUsername();
    void login();
}

package model.auth.exceptions;

/**
 * Created by cole on 9/14/16.
 */
public class InvalidPasswordException extends AuthenticationException {
    public InvalidPasswordException () {
        super("Password is incorrect");
    }
}

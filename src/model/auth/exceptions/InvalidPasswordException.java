package model.auth.exceptions;

/**
 * Indicates the password is incorrect
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class InvalidPasswordException extends AuthenticationException {
    public InvalidPasswordException () {
        super("Password is incorrect");
    }
}

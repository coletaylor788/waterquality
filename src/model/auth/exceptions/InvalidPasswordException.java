package model.auth.exceptions;

/**
 * Indicates the password is incorrect
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class InvalidPasswordException extends AuthenticationException {
    /**
     * Creates an invalid password exception
     */
    public InvalidPasswordException () {
        super("Password is incorrect");
    }
}

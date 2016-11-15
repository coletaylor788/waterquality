package model.auth.exceptions;

/**
 * Indicates the username is incorrect
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class InvalidUsernameException extends AuthenticationException {
    /**
     * Creates an invalid username exception
     */
    public InvalidUsernameException() {
        super("Username is incorrect");
    }
}

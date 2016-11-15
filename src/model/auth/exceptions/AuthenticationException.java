package model.auth.exceptions;

/**
 * Indicates an exception with authentication
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class AuthenticationException extends Exception {
    /**
     * Creates the exception
     * @param message is the message to pass along
     */
    public AuthenticationException(String message) {
        super(message);
    }
}

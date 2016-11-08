package model.auth.exceptions;

/**
 * Indicates an exception with authentication
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class AuthenticationException extends Exception {
    AuthenticationException(String message) {
        super(message);
    }
}

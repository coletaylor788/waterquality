package model.auth.exceptions;

/**
 * Indicates email is in an invalid format
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class InvalidEmailException extends AuthenticationException{
    /**
     * Creates an invalid email exception
     */
    public InvalidEmailException() {
        super("Email has an invalid format");
    }
}

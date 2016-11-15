package model.auth.exceptions;

import model.auth.exceptions.AuthenticationException;

/**
 * Indicates there was an exception with creating a user
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class UnableToCreateUserException extends AuthenticationException {
    /**
     * Creates an exception indication the user couldn't be created
     * @param message is the message to pass along
     */
    public UnableToCreateUserException(String message) {
        super(message);
    }
}

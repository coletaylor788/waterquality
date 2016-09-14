package model.auth.exceptions;

import model.auth.exceptions.AuthenticationException;

/**
 * Created by cole on 9/14/16.
 */
public class UnableToCreateUserException extends AuthenticationException {
    public UnableToCreateUserException(String message) {
        super(message);
    }
}

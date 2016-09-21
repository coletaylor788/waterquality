package model.auth.exceptions;

/**
 * Created by cole on 9/21/16.
 */
public class UnableToSetPasswordException extends AuthenticationException {
    public UnableToSetPasswordException(String message) {
        super(message);
    }
}

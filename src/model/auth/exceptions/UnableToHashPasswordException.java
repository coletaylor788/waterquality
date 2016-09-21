package model.auth.exceptions;

/**
 * Created by cole on 9/21/16.
 */
public class UnableToHashPasswordException extends AuthenticationException {
    public UnableToHashPasswordException(String message) {
        super(message);
    }
}

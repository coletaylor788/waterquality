package model.auth.exceptions;

/**
 * Created by cole on 9/14/16.
 */
public class InvalidUsernameException extends AuthenticationException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}

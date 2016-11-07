package model.auth.exceptions;

/**
 * Created by cole on 9/14/16.
 */
public class AuthenticationException extends Exception {
    AuthenticationException(String message) {
        super(message);
    }
}

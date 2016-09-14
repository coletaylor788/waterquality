package model.auth;

/**
 * Created by cole on 9/14/16.
 */
public class UnableToCreateUserException extends Exception {
    public UnableToCreateUserException(String message) {
        super(message);
    }
}

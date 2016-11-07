package model.auth.exceptions;

/**
 * Created by cole on 9/21/16.
 */
public class InvalidEmailException extends AuthenticationException{
    public InvalidEmailException() {
        super("Email has an invalid format");
    }
}

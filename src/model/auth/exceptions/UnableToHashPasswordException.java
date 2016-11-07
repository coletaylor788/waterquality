package model.auth.exceptions;

/**
 * Created by cole on 9/21/16.
 */
public class UnableToHashPasswordException extends AuthenticationException {
    public UnableToHashPasswordException() {
        super("Unable to get provider for SHA-256 algorithm");
    }
}

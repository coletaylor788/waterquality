package model.auth.exceptions;

/**
 * Indicates their was an exception with hashing the password
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class UnableToHashPasswordException extends AuthenticationException {
    public UnableToHashPasswordException() {
        super("Unable to get provider for SHA-256 algorithm");
    }
}

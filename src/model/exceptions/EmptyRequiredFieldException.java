package model.exceptions;

/**
 * Indicates a required field is empty
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class EmptyRequiredFieldException extends Exception{

    /**
     * Creates the exception
     * @param message the message to pass along
     */
    public EmptyRequiredFieldException(String message) {
        super(message);
    }
}

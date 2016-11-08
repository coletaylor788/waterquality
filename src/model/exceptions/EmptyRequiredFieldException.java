package model.exceptions;

/**
 * Indicates a required field is empty
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class EmptyRequiredFieldException extends Exception{
    public EmptyRequiredFieldException(String message) {
        super(message);
    }
}

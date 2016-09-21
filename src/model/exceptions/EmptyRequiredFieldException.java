package model.exceptions;

/**
 * Created by cole on 9/21/16.
 */
public class EmptyRequiredFieldException extends Exception{
    public EmptyRequiredFieldException(String message) {
        super(message);
    }
}

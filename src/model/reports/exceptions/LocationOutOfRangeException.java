package model.reports.exceptions;

/**
 * Indicates the Location is invalid
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class LocationOutOfRangeException extends Exception {
    public LocationOutOfRangeException(String message) {
        super(message);
    }
}

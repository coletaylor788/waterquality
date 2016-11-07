package model.reports;

/**
 * Water Type Enumeration
 *
 * @author Cole Taylor
 * @version 1.0
 */
public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private final String type;

    WaterType(String type) {
        this.type = type;
    }

    /**
     * @return a String representation of the WaterType
     */
    @Override
    public String toString() {
        return this.type;
    }
}

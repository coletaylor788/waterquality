package model.reports;

/**
 * Water Condition Enumeration
 *
 * @author Cole Taylor
 * @version 1.0
 */
public enum WaterCondition {

    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private final String condition;

    WaterCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return a String representation of a water condition
     */
    @Override
    public String toString() {
        return this.condition;
    }
}

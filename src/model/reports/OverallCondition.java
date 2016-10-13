package model.reports;

/**
 * Overall Condition Enumerator
 *
 * Created by Nkosi Kee on 10/6/2016.
 */
public enum OverallCondition {

    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private String condition;

    private OverallCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return a String representation of a Overall condition
     */
    @Override
    public String toString() {
        return this.condition;
    }
}
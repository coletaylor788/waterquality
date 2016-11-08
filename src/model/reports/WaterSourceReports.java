package model.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Container class to contain all Water Source Reports
 *
 * This is a singleton class since there should only
 * be one instance of this class
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class WaterSourceReports implements Serializable {

    private final List<SourceReport> sourceReports;
    private static WaterSourceReports instance = null;

    /**
     * Private constructor to prevent instantiation
     */
    private WaterSourceReports() {
        sourceReports = new ArrayList<>();
    }

    /**
     * Adds a source report to the list
     *
     * @param sourceReport is the source report to add
     */
    public void addSourceReport(SourceReport sourceReport) {
        sourceReports.add(sourceReport);
    }

    /**
     * @return the List of source reports
     */
    public List<SourceReport> getSourceReports() {
        return sourceReports;
    }

    /**
     * Gets the one instance of this class.
     * Creates an instance if it doesn't exist.
     *
     * @return the WaterSourceReports instance.
     */
    public static WaterSourceReports getInstance() {
        if (instance == null) {
            instance = new WaterSourceReports();
        }
        return instance;
    }
}

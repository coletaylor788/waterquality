package model.reports;

import controller.MainController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Patrick on 11/3/2016.
 */
public class PurityReportGraph {

    private Map<Integer, Double> reportsToGraph;
    private List<PurityReport> purityReports;

    /**
     * Creates a map that are the coordinates for the graph of the historical report
     * @param latitude of the location
     * @param longitude of location
     * @param isVirusPPM whether we are checking for Virus or Containment PPM
     */
    public PurityReportGraph(double latitude, double longitude, boolean isVirusPPM) {
        reportsToGraph = new HashMap<>();
        purityReports = (List<PurityReport>) MainController.getInstance().getFacade().getPurityReports();
        Location location = new Location(latitude, longitude);

        for (PurityReport report: purityReports) {
            if (report.getLocation().equals(location)) {
                int month = report.getTimestamp().getMonth();
                double ppm = report.getContaminantPPM();
                if (isVirusPPM) {
                    ppm = report.getVirusPPM();
                }

                if (reportsToGraph.containsKey(month)) {
                    double newPpm = reportsToGraph.get(month);
                    ppm = (ppm + newPpm) / 2;
                }
                reportsToGraph.put(month, ppm);
            }
        }
    }

    /**
     * Nested constructor without Virus PPM boolean, defaults to true
     * @param latitude of the location
     * @param longitude of location
     */
    public PurityReportGraph(double latitude, double longitude) {
        this(latitude, longitude, true);
    }

    /**
     * Gives you the map created in the constructor
     * @return Map of the points for the graph
     */
    public Map<Integer, Double> getCoordinates() {
        return reportsToGraph;
    }

}

package model.reports;

import controller.MainController;
import model.exceptions.EmptyRequiredFieldException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Patrick on 11/3/2016.
 */
public class PurityReportGraph {

    private Map<String, Double> reportsToGraph;
    private List<PurityReport> purityReports;

    /**
     * Creates a map that are the coordinates for the graph of the historical report
     * @param latitude of the location
     * @param longitude of location
     * @param isVirusPPM whether we are checking for Virus or Containment PPM
     */
    public PurityReportGraph(double latitude, double longitude, boolean isVirusPPM) throws EmptyRequiredFieldException {
        reportsToGraph = new HashMap<>();
        purityReports = (List<PurityReport>) MainController.getInstance().getFacade().getPurityReports();
        Location location = new Location(latitude, longitude);

        for (PurityReport report: purityReports) {
            if (report.getLocation().equals(location)) {
                int mon = report.getTimestamp().getMonth() + 1;
                String month = monthToNumber(mon);
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
    public PurityReportGraph(double latitude, double longitude) throws EmptyRequiredFieldException {
        this(latitude, longitude, true);
    }

    /**
     * Gives you the map created in the constructor
     * @return Map of the points for the graph
     */
    public Map<String, Double> getCoordinates() {
        return reportsToGraph;
    }

    /**
     * Converts number to month
     * @param number month number
     * @return String value of month
     */
    private String monthToNumber(int number) {
        String monthString;
        switch (number) {
            case 1:  monthString = "Jan";
                break;
            case 2:  monthString = "Feb";
                break;
            case 3:  monthString = "Mar";
                break;
            case 4:  monthString = "Apr";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "Jun";
                break;
            case 7:  monthString = "Jul";
                break;
            case 8:  monthString = "Aug";
                break;
            case 9:  monthString = "Sep";
                break;
            case 10: monthString = "Oct";
                break;
            case 11: monthString = "Nov";
                break;
            case 12: monthString = "Dec";
                break;
            default: monthString = "Jan";
                break;
        }
        return monthString;
    }

}

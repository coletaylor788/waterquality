package model.reports;

import controller.MainController;

import java.util.*;

/**
 * Creates the data for a history graph of purity reports
 *
 * @author Patrick Black
 * @version 1.0
 */
public class PurityReportGraph {

    private final Map<String, Double> reportsToGraph;

    /**
     * Creates a map that are the coordinates for the graph of the historical report
     * @param latitude of the location
     * @param longitude of location
     * @param isVirusPPM whether we are checking for Virus or Containment PPM
     */
    public PurityReportGraph(int year, double latitude, double longitude, boolean isVirusPPM) {
        reportsToGraph = new HashMap<>();
        List<PurityReport> purityReports = MainController.getInstance().getFacade()
                .getPurityReports().getPurityReports();
        Location location = new Location(latitude, longitude);

        for (PurityReport report: purityReports) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(report.getTimestamp());
            if (report.getLocation().equals(location) && year == calendar.get(Calendar.YEAR)) {
                int mon = calendar.get(Calendar.MONTH) + 1;
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

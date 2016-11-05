package model.reports;

import javafx.beans.property.*;
import model.auth.Role;
import model.auth.User;
import model.exceptions.EmptyRequiredFieldException;

import java.io.Serializable;
import java.security.AccessControlException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contains information for a Water Purity Report
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class PurityReport implements Serializable {

    private static int nextID = 1;

    private SimpleObjectProperty<Date> timestamp = new SimpleObjectProperty<>();
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleObjectProperty<User> reportedWorker = new SimpleObjectProperty<>();
    private SimpleObjectProperty<OverallCondition> overallCondition = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Location> location = new SimpleObjectProperty<>();
    private SimpleDoubleProperty virusPPM = new SimpleDoubleProperty();
    private SimpleDoubleProperty contaminantPPM = new SimpleDoubleProperty();

    /**
     * Creates a Purity report
     *
     * @param reportedWorker the worker that made the report
     * @param overallCondition the condition of the water
     * @param virusPPM amount of viruses in PPM
     * @param contaminantPPM amount of contaminants in PPM
     *
     * @throws NumberFormatException when virus or contaminant PPM is negative
     * @throws AccessControlException if a USER or ADMIN attempts to create a purity report
     */
    public PurityReport(User reportedWorker, OverallCondition overallCondition, Location location,
                        double virusPPM, double contaminantPPM)
            throws AccessControlException, NumberFormatException, EmptyRequiredFieldException {
        this(new Date(), reportedWorker, overallCondition, location, virusPPM, contaminantPPM);
    }

    /**
     * Creates a purity report w/o auto variable assignment
     *
     * @param timestamp is the time of the report
     * @param reportedWorker is the worker who submitted the report
     * @param overallCondition is the condition of the report
     * @param location is the location of the report
     * @param virusPPM is the virus amount in the report
     * @param contaminantPPM is the contaminant amount in the report
     * @throws AccessControlException When a User or Admin attempts to create
     * @throws NumberFormatException if there are invalid numbers passed in
     * @throws EmptyRequiredFieldException if a required field is omitted.
     */
    public PurityReport(Date timestamp, User reportedWorker, OverallCondition overallCondition,
                         Location location, double virusPPM, double contaminantPPM)
            throws AccessControlException, NumberFormatException, EmptyRequiredFieldException {
        this.timestamp.set(timestamp);
        this.id.set(nextID);
        this.location.set(location);
        nextID++;
        setReportedWorker(reportedWorker);
        setOverallCondition(overallCondition);
        setVirusPPM(virusPPM);
        setContaminantPPM(contaminantPPM);
    }

    /**
     * String Representation of the Source Report
     * This will probably only be used for the list and testing
     *
     * @return ID | Timestamp | Location | WaterType | WaterLocation | ReportedUser
     */
    @Override
    public String toString() {
        return getID() + " | " + getStringTimestamp() + " | " + getLocation()
                + " | " + getOverallCondition() + " | Virus: " + getVirusPPM() + " | Contaminant: "
                + getContaminantPPM() + " | " + getReportedWorker();
    }

    // ============= SETTERS =============

    /**
     * Sets the reported worker
     * @param reportedWorker is the worker to set it to
     * @throws AccessControlException when a User or Admin attempts to create a purity report
     */
    public void setReportedWorker(User reportedWorker) throws AccessControlException {
        if (reportedWorker.getRole() != Role.WORKER
                && reportedWorker.getRole() != Role.MANAGER) {
            throw new AccessControlException("Must be a Worker or Manager to create a Purity report");
        }
        this.reportedWorker.set(reportedWorker);
    }

    /**
     * Sets the overall condition
     * @param overallCondition is the overall condition to set it to
     */
    public void setOverallCondition(OverallCondition overallCondition)
            throws EmptyRequiredFieldException {
        if (overallCondition == null) {
            throw new EmptyRequiredFieldException("Overall condition cannot be empty");
        }
        this.overallCondition.set(overallCondition);
    }

    /**
     * Sets the virus PPM
     * @param virusPPM is the decimal value for virus PPM
     * @throws NumberFormatException when virusPPM is negative
     */
    public void setVirusPPM(double virusPPM) throws NumberFormatException {
        if (virusPPM < 0) {
            throw new NumberFormatException("Virus PPM cannot be negative");
        }
        this.virusPPM.set(virusPPM);
    }

    /**
     * Sets the contaminant PPM
     * @param contaminantPPM is the decimal value for contaminant PPM
     * @throws NumberFormatException when contaminantPPM is negative
     */
    public void setContaminantPPM(double contaminantPPM) throws NumberFormatException {
        if (contaminantPPM < 0) {
            throw new NumberFormatException("Contaminant PPM cannot be negative");
        }
        this.contaminantPPM.set(contaminantPPM);
    }

    // ============= GETTERS =============

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp.get();
    }

    /**
     * @return Time the report was created as a formatted string
     */
    public String getStringTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return dateFormat.format(timestamp.get());
    }

    /**
     * @return the id
     */
    public int getID() {
        return id.get();
    }

    /**
     *
     * @return the reported user
     */
    public User getReportedWorker() {
        return reportedWorker.get();
    }

    /**
     * @return the overall condition
     */
    public OverallCondition getOverallCondition() {
        return overallCondition.get();
    }

    /**
     * @return the virus PPM
     */
    public double getVirusPPM() {
        return virusPPM.get();
    }

    /**
     * @return the contaminant PPM
     */
    public double getContaminantPPM() {
        return contaminantPPM.get();
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location.get();
    }
}

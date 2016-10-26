package model.reports;

import javafx.beans.property.*;
import model.auth.User;

import java.util.Date;

/**
 * Contains information for a Water Purity Report
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class PurityReport {

    private static int nextID = 1;

    private ObjectProperty<Date> timestamp = new SimpleObjectProperty<>();
    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<User> reportedWorker = new SimpleObjectProperty<>();
    private ObjectProperty<OverallCondition> overallCondition = new SimpleObjectProperty<>();
    private DoubleProperty virusPPM = new SimpleDoubleProperty();
    private DoubleProperty contaminantPPM = new SimpleDoubleProperty();

    /**
     * Creates a Purity report
     *
     * @param reportedWorker the worker that made the report
     * @param overallCondition the condition of the water
     * @param virusPPM amount of viruses in PPM
     * @param contaminantPPM amount of contaminants in PPM
     */
    public PurityReport(User reportedWorker, OverallCondition overallCondition,
                        double virusPPM, double contaminantPPM) {
        this(new Date(), reportedWorker, overallCondition, virusPPM, contaminantPPM);
    }

    private PurityReport(Date timestamp, User reportedWorker, OverallCondition overallCondition,
                         double virusPPM, double contaminantPPM) {
        this.timestamp.set(timestamp);
        this.id.set(nextID);
        nextID++;
        setReportedWorker(reportedWorker);
        setOverallCondition(overallCondition);
        setVirusPPM(virusPPM);
        setContaminantPPM(contaminantPPM);
    }

    // ============= SETTERS =============

    /**
     * Sets the reported worker
     * @param reportedWorker is the worker to set it to
     */
    public void setReportedWorker(User reportedWorker) {
        this.reportedWorker.set(reportedWorker);
    }

    /**
     * Sets the overall condition
     * @param overallCondition is the overall condition to set it to
     */
    public void setOverallCondition(OverallCondition overallCondition) {
        this.overallCondition.set(overallCondition);
    }

    /**
     * Sets the virus PPM
     * @param virusPPM is the decimal value for virus PPM
     */
    public void setVirusPPM(double virusPPM) {
        this.virusPPM.set(virusPPM);
    }

    /**
     * Sets the contaminant PPM
     * @param contaminantPPM is the decimal value for contaminant PPM
     */
    public void setContaminantPPM(double contaminantPPM) {
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
}

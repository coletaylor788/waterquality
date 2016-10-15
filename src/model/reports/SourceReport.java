package model.reports;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import model.auth.User;
import model.exceptions.EmptyRequiredFieldException;

import javax.xml.transform.Source;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

/**
 * A report about a water source which can be created by users
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class SourceReport {

    private static int nextID = 1;

    private ObjectProperty<Date> timestamp = new SimpleObjectProperty<>();
    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<User> reportedUser = new SimpleObjectProperty<>();
    private ObjectProperty<Location> location = new SimpleObjectProperty<>();
    private ObjectProperty<WaterType> waterType = new SimpleObjectProperty<>();
    private ObjectProperty<WaterCondition> waterCondition = new SimpleObjectProperty<>();

    /**
     * Create a new SourceReport
     *
     * @param reportedUser is the user who reports the SourceReport
     * @param location is the location of the SourceReport
     * @param waterType is the waterType of the SourceReport
     * @param waterCondition is the waterCondition of the SourceReport
     */
    public SourceReport(User reportedUser, Location location, WaterType waterType,
                        WaterCondition waterCondition) throws EmptyRequiredFieldException {
        this(new Date(), reportedUser, location, waterType, waterCondition);
    }

    private SourceReport(Date timestamp, User reportedUser, Location location,
                        WaterType waterType, WaterCondition waterCondition) throws EmptyRequiredFieldException {
        if (waterType == null) {
            throw new EmptyRequiredFieldException("Water type cannot be empty");
        } else if (waterCondition == null) {
            throw new EmptyRequiredFieldException("Water condition cannot be empty");
        }

        this.id.set(nextID);
        nextID++;

        this.timestamp.set(timestamp);
        this.reportedUser.set(reportedUser);
        this.location.set(location);
        this.waterType.set(waterType);
        this.waterCondition.set(waterCondition);
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
                + " | " + getWaterType() + " | " + getWaterCondition()
                + " | " + getReportedUser();
    }

    // ************** GETTERS **************
    /**
     * @return Time the report was created as a Date object
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
     * @return the ID of the report
     */
    public int getID() {
        return id.get();
    }

    /**
     * @return the User who made the report
     */
    public User getReportedUser() {
        return reportedUser.get();
    }

    /**
     * @return the Location of the report
     */
    public Location getLocation() {
        return location.get();
    }

    /**
     * @return the Water Type of the report
     */
    public WaterType getWaterType() {
        return waterType.get();
    }

    /**
     * @return the Water Condition of the report
     */
    public WaterCondition getWaterCondition() {
        return waterCondition.get();
    }

    //***************** Setters **************
    /**
     * @param waterType type passed
     */
    public void setWaterType(WaterType waterType) {
        this.waterType.set(waterType);
    }

    /**
     * @param location type passed
     */
    public void setLocation (Location location) {
        this.location.set(location);
    }

    /**
     * @param condition type passed
     */
    public void setCondition (WaterCondition condition) {
        this.waterCondition.set(condition);
    }

}

package model;

import model.auth.UsersData;
import model.reports.SourceReport;
import model.reports.WaterSourceReports;

/**
 * Main Facade that connects the controllers to the model
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class Facade {
    private UsersData users = new UsersData();
    private WaterSourceReports sourceReports;
    private SourceReport currSourceReport;

    private static Facade instance = null;

    /**
     * Private constructor to create the one Facade instance
     */
    private Facade() {
        users = new UsersData();
        sourceReports = WaterSourceReports.getInstance();
    }

    /* ============== GETTERS ============== */
    public UsersData getUsers() {
        return users;
    }
    public WaterSourceReports getSourceReports() {
        return sourceReports;
    }

    /**
     * Returns the single instance of the class
     * @return the Facade instance
     */
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }
}

package model;

import model.auth.UsersData;
import model.reports.WaterPurityReports;
import model.reports.WaterSourceReports;

import java.io.Serializable;

/**
 * Main Facade that connects the controllers to the model
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class Facade implements Serializable {
    private UsersData users = new UsersData();
    private WaterSourceReports sourceReports;
    private WaterPurityReports purityReports;

    private static Facade instance = null;

    /**
     * Private constructor to create the one Facade instance
     */
    private Facade() {
        users = new UsersData();
        sourceReports = WaterSourceReports.getInstance();
        purityReports = WaterPurityReports.getInstance();
    }

    /* ============== GETTERS ============== */
    public UsersData getUsers() {
        return users;
    }
    public WaterSourceReports getSourceReports() {
        return sourceReports;
    }
    public WaterPurityReports getPurityReports() { return purityReports; }

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

    /**
     * Used to load the Facade from a JSON file
     *
     * @param instance is the instance to load
     */
    public static void setInstance(Facade instance) {
        Facade.instance = instance;
    }
}

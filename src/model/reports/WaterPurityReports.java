package model.reports;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Facade;
import model.auth.Role;
import model.exceptions.EmptyRequiredFieldException;

import javax.swing.border.EmptyBorder;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Nkosi Kee on 10/27/2016.
 */
public class WaterPurityReports implements Serializable {

    private final List<PurityReport> purityReports;
    private static WaterPurityReports instance = null;
    private static boolean createdSamples = false;

    private WaterPurityReports() {
        purityReports = new ArrayList<>();
    }

    public void addPurityReport(PurityReport report) {
        purityReports.add(report);
    }

    public List<PurityReport> getPurityReports() {
        return purityReports;
    }

    public ObservableList<PurityReport> getObservablePurityReports() {
        return FXCollections.observableList(purityReports);
    }

    public static WaterPurityReports getInstance() {
        if (instance == null) {
            instance = new WaterPurityReports();
        }
        return instance;
    }

    /**
     * Creates sample purity reports for testing historical report
     */
    public void createSampleReports() {
        if (!createdSamples
                && (Facade.getInstance().getUsers().getCurrentUser().getRole() == Role.MANAGER
                || Facade.getInstance().getUsers().getCurrentUser().getRole() == Role.MANAGER)) {
            createdSamples = true;
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(2016, 0, 1);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.1,
                        0.8));
                cal.set(2016, 1, 2);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.3,
                        0.6));
                cal.set(2016, 1, 15);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.5,
                        0.6));
                cal.set(2016, 2, 1);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.2,
                        0.4));
                cal.set(2016, 2, 4);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.4,
                        0.5));
                cal.set(2016, 2, 20);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.5,
                        0.5));
                cal.set(2016, 10, 5);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.9,
                        0.2));
                cal.set(2016, 11, 5);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.95,
                        0.1));
                cal.set(2015, 3, 20);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.5,
                        0.1));
                cal.set(2015, 8, 20);
                purityReports.add(new PurityReport(cal.getTime(),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.5,
                        0.1));
            } catch (EmptyRequiredFieldException e) {
                MainController.getInstance().showAlertMessage("Required field is empty");
            }
        }
    }
}

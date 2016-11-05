package model.reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Facade;
import model.auth.Role;
import model.exceptions.EmptyRequiredFieldException;

import javax.swing.border.EmptyBorder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nkosi Kee on 10/27/2016.
 */
public class WaterPurityReports implements Serializable {

    private List<PurityReport> purityReports;
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
                purityReports.add(new PurityReport(new Date(2016, 01, 01),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.1,
                        0.1));
                purityReports.add(new PurityReport(new Date(2016, 02, 02),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.1,
                        0.1));
                purityReports.add(new PurityReport(new Date(2016, 02, 15),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.5,
                        0.5));
                purityReports.add(new PurityReport(new Date(2016, 03, 01),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.2,
                        0.2));
                purityReports.add(new PurityReport(new Date(2016, 03, 04),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.4,
                        0.4));
                purityReports.add(new PurityReport(new Date(2016, 03, 20),
                        Facade.getInstance().getUsers().getCurrentUser(),
                        OverallCondition.SAFE,
                        new Location(0, 0),
                        0.5,
                        0.5));
            } catch (EmptyRequiredFieldException e) {
                System.out.println(e);
            }
        }
    }
}

package model.reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nkosi Kee on 10/27/2016.
 */
public class WaterPurityReports {

    private List<PurityReport> purityReports;
    private static WaterPurityReports instance = null;

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
}

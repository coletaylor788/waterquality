package controller;

/**
 * Created by Nkosi Kee on 10/28/2016.
 */

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.reports.PurityReport;

public class ViewWaterPurityReportController {

    @FXML
    private ListView<PurityReport> purityReportList;

    @FXML
    public void initialize () {
        ObservableList<PurityReport> purityReports = MainController.getInstance().getFacade().getPurityReports().getObservablePurityReports();
        purityReportList.setItems(purityReports);
    }

    @FXML
    void handleCancelPressed() {
        MainController.getInstance().changeScene("../view/Home.fxml", "Home");
    }
}

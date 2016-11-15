package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.reports.PurityReport;

/**
 * Controller for viewing water reports
 *
 * @author Nkosi Kee
 * @version 1.0
 */
public class ViewWaterPurityReportController {

    @FXML
    private ListView<PurityReport> purityReportList;

    /**
     * Initializes the scene
     */
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

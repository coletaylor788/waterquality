package controller.unused;

import controller.MainController;
import controller.unused.WaterAvailabilityReportController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import model.auth.ReportType;
import model.auth.Role;
import model.auth.User;
import model.reports.SourceReport;
import sample.Main;

import java.io.IOException;
import java.util.ArrayList;


public class UserScreenController {

    @FXML
    private ListView<SourceReport> sourceReportsList;

    @FXML
    Button submitSourceReport;

    @FXML
    private void initialize() {
        updateSourceReportsList();
        setVisibleFields();
    }

    public void setVisibleFields() {
        User currUser = MainController.getInstance().getFacade().getUsers().getCurrentUser();
        if (currUser.getRole().equals(Role.USER)) {
            submitSourceReport.setVisible(true);
        } else if (currUser.getRole().equals(Role.WORKER)) {
            submitSourceReport.setVisible(true);
        } else if (currUser.getRole().equals(Role.MANAGER)) {
            submitSourceReport.setVisible(true);
        } else { // Admin
            submitSourceReport.setVisible(false);
        }
    }

    private void updateSourceReportsList() {
        sourceReportsList.setItems(
                MainController.getInstance().getFacade().getSourceReports().getObservableSourceReports());
    }

    @FXML
    /**
     * handles Logout button
     */
    private void handleLogoutPressed() throws Exception {
        MainController.getInstance().changeScene("../view/LoginScreen.fxml", "Login");
    }

    @FXML
    /**
     * handles Edit button
     */
    private void handleEditPressed() {
        MainController.getInstance().changeScene("../view/EditProfileScreen.fxml", "Edit Profile");
    }

    /**
     * Displays the water source report scene
     */
    @FXML
    private void handleSubmitSourceReportPressed() {
        MainController.getInstance().changeScene("../view/WaterSourceReport.fxml", "Water Source Report");
    }
}

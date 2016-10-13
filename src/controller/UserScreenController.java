package controller;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.auth.ReportType;
import model.auth.Role;
import model.auth.User;

import java.io.IOException;
import java.util.ArrayList;


public class UserScreenController {

    private MainController mainController;
    private User user;

<<<<<<< HEAD


    @FXML
    private ComboBox<ReportType> reportType;

    private ObservableList generateReports() {
        user = mainController.getUsersData().getCurrentUser();
        Role role = user.getRole();
        ArrayList<ReportType> repList = new ArrayList<>();
        if(role == Role.ADMIN) {
            repList.add(ReportType.VIEWUSERS);
            repList.add(ReportType.VIEWSECUR);
        } else {
            repList.add(ReportType.MAKEAVAI);
            repList.add(ReportType.VIEWAVAI);
            if( role != Role.USER) {
                repList.add(ReportType.MAKEPURI);
                if( role != Role.WORKER) {
                    repList.add(ReportType.VIEWHIST);
                    repList.add(ReportType.VIEWREPS);
                }
            }
        }
        ObservableList<ReportType> repsList = FXCollections.observableArrayList(repList);
        return repsList;
    }

    /**
     * Sets the main controller
     * @param mainController controller passed
=======
    /**
     * Passes in the Main Controller in order to preserve several properties
     * @param mainController the class with the properties
>>>>>>> 62de37d4ed8c1e69e362f72a2e576cd70cad4bd6
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        reportType.getItems().addAll(generateReports());
    }

    @FXML
    /**
     * handles Logout button
     */
    private void handleLogoutPressed() throws Exception {
        try {
            mainController.getUsersData().logout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("../view/LoginScreen.fxml"));
            BorderPane loginScreen = loader.load();
            LoginScreenController controller = loader.getController();
            controller.setMainController(mainController);

            //sets the scene
            Stage primaryStage = mainController.getPrimaryStage();
            primaryStage.setTitle("Login Screen");
            primaryStage.setScene(new Scene(loginScreen));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    /**
     * handles Edit button
     */
    private void handleEditPressed() {
        user = mainController.getUsersData().getCurrentUser();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("../view/EditProfileScreen.fxml"));
            BorderPane editScreen = loader.load();
            EditProfileScreenControl controller = loader.getController();
            controller.setMainController(mainController);
            controller.setDefaultFields();

            // Sets the scene
            Stage primaryStage = mainController.getPrimaryStage();
            primaryStage.setTitle("Edit User: "
                    + mainController.getUsersData().getCurrentUser().getFirstName());
            primaryStage.setScene(new Scene(editScreen));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleActivatePressed() {
        user = mainController.getUsersData().getCurrentUser();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(reportType.getValue().getPath()));
            BorderPane reportScreen = loader.load();
            WaterAvailabilityReportController controller = loader.getController();
            controller.setMainController(mainController);

            // Sets the scene
            Stage primaryStage = mainController.getPrimaryStage();
            primaryStage.setTitle(reportType.getValue().getTitle());
            primaryStage.setScene(new Scene(reportScreen));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}

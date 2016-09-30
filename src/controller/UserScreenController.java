package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.auth.User;

import java.io.IOException;


public class UserScreenController {

    private MainController mainController;
    private User user;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
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
}

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
import model.auth.UsersData;
import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidPasswordException;
import model.auth.exceptions.InvalidUsernameException;

import java.io.IOException;


public class LoginScreenController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private User user;
    private UsersData usersdata = new UsersData();
    private Stage _dialogStage;
    private MainController mainController;

    //closes dialogue box
    @FXML
    private void handleCloseMenu() {
        System.exit(0);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleLoginPressed() {
        try {
            user = usersdata.login(usernameField.getText(), passwordField.getText());
        } catch (InvalidUsernameException e) { // Catches all exceptions; Change to catch individual exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        } catch (InvalidPasswordException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        } catch (AuthenticationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Not a valid user.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRegisterPressed() throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("../view/RegisterScreen.fxml"));
            BorderPane registerScreen = loader.load();
            RegisterScreenController controller = loader.getController();
            controller.setMainController(mainController);

            //sets the scene
            Stage primaryStage = mainController.getPrimaryStage();
            primaryStage.setTitle("Register Screen");
            primaryStage.setScene(new Scene(registerScreen));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

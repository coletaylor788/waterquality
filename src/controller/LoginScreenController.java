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
    private Stage _dialogStage;

    //closes dialogue box
    @FXML
    private void handleCloseMenu() {
        System.exit(0);
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleLoginPressed() {
        try {
            user = MainController.getInstance().getFacade().getUsers().login(usernameField.getText(), passwordField.getText());
            MainController.getInstance().changeScene("../view/Home.fxml", "Home");
        } catch (InvalidUsernameException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Invalid Username");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        } catch (InvalidPasswordException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Invalid Password");
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
    private void handleRegisterPressed() {
        MainController.getInstance().changeScene("../view/RegisterScreen.fxml", "Register");
    }

    @FXML
    private void handleLoadPressed() {
        //MainController.getInstance().getPersistenceManager().loadFromBinary(file);
    }

    @FXML
    private void handleSavePressed() {
        //MainController.getInstance().getPersistenceManager().saveFromBinary(file);

    }
}

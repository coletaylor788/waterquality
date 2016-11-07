package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import model.Facade;
import model.auth.User;
import model.auth.UsersData;
import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.InvalidPasswordException;
import model.auth.exceptions.InvalidUsernameException;

import java.io.File;
import java.io.IOException;


public class LoginScreenController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleLoginPressed() {
        try {
            User user = MainController.getInstance().getFacade().getUsers().login(usernameField.getText(), passwordField.getText());
            //Facade.getInstance().getPurityReports().createSampleReports();
            MainController.getInstance().changeScene("../view/Home.fxml", "Home");
        } catch (InvalidUsernameException | InvalidPasswordException e) {
            MainController.getInstance().showAlertMessage(e.getMessage());
        } catch (AuthenticationException e) {
            MainController.getInstance().showAlertMessage("Not a valid user.");
        }
    }

    @FXML
    private void handleRegisterPressed() {
        MainController.getInstance().changeScene("../view/RegisterScreen.fxml", "Register");
    }

    @FXML
    private void handleLoadPressed() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Binary File");
        File file  = fc.showOpenDialog(MainController.getInstance().getPrimaryStage());
        if (file != null) {
            //MainController.getInstance().getPersistenceManager().loadFromBinary(file);
            MainController.getInstance().getPersistenceManager().loadFromJsonfile(file);
        }
    }

    @FXML
    private void handleSavePressed() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Binary File");
        File file  = fc.showSaveDialog(MainController.getInstance().getPrimaryStage());
        if (file != null) {
            //MainController.getInstance().getPersistenceManager().saveToBinary(file);
            MainController.getInstance().getPersistenceManager().saveToJson(file);
        }
    }
}

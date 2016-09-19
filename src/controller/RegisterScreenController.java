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
import model.auth.exceptions.UnableToCreateUserException;

import java.io.IOException;

public class RegisterScreenController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private User user;
    private UsersData usersdata = new UsersData();
    private MainController mainController;
    private Stage _dialogStage;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleRegisterPressed() {
        try {
            usersdata.addUser(usernameField.getText(),
                                passwordField.getText(),
                                firstNameField.getText(),
                                lastNameField.getText(),
                                emailField.getText());

        } catch (UnableToCreateUserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Cannot Create User");
            alert.setHeaderText("Try again with different information.");
            alert.showAndWait();
        }

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("../view/UserScreen.fxml"));
            BorderPane userScreen = loader.load();
            LoginScreenController controller = loader.getController();
            controller.setMainController(mainController);

            //sets the scene
            Stage primaryStage = mainController.getPrimaryStage();
            primaryStage.setTitle("User: " + firstNameField.getText());
            primaryStage.setScene(new Scene(userScreen));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleCancelPressed() throws Exception {
        try {
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
}

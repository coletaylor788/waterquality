package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import model.auth.Role;
import model.auth.User;
import model.auth.UsersData;
import model.auth.exceptions.AuthenticationException;
import model.auth.exceptions.UnableToCreateUserException;
import model.exceptions.EmptyRequiredFieldException;

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

    @FXML
    private ComboBox<Role> role;

    private User user;
    private MainController mainController;
    private Stage _dialogStage;

    @FXML
    private void initialize() {
        role.getItems().addAll(generateRoles());
    }

    private static ObservableList generateRoles() {
        Role[] roles = Role.values();
        ObservableList<Role> roleList = FXCollections.observableArrayList();
        for (Role role : roles) {
            roleList.add(role);
        }
        return roleList;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleRegisterPressed() {
        try {
            mainController.getUsersData().addUser(usernameField.getText(),
                                passwordField.getText(),
                                firstNameField.getText(),
                                lastNameField.getText(),
                                role.getValue(),
                                emailField.getText());
            mainController.getUsersData().login(usernameField.getText(), passwordField.getText());

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainController.class.getResource("../view/UserScreen.fxml"));
                BorderPane userScreen = loader.load();
                UserScreenController controller = loader.getController();
                controller.setMainController(mainController);

                // Sets the scene
                Stage primaryStage = mainController.getPrimaryStage();
                primaryStage.setTitle("User: " + firstNameField.getText());
                primaryStage.setScene(new Scene(userScreen));
                primaryStage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle(e.getMessage());
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
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

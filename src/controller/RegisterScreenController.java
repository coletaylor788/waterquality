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
    private Stage _dialogStage;

    @FXML
    /**
     * populates combo boxes
     */
    private void initialize() {
        role.getItems().addAll(generateRoles());
    }

    /**
     * Creates an observable list to put in the ComboBox
     * @return list with all the roles
     */
    private static ObservableList generateRoles() {
        Role[] roles = Role.values();
        ObservableList<Role> roleList = FXCollections.observableArrayList();
        for (Role role : roles) {
            roleList.add(role);
        }
        return roleList;
    }

    @FXML
    /**
     * handles Register button
     */
    private void handleRegisterPressed() {
        try {
            MainController.getInstance().getFacade().getUsers().addUser(usernameField.getText(),
                                passwordField.getText(),
                                firstNameField.getText(),
                                lastNameField.getText(),
                                role.getValue(),
                                emailField.getText());
            MainController.getInstance().getFacade().getUsers().login(usernameField.getText(), passwordField.getText());

            MainController.getInstance().changeScene("../view/UserScreen.fxml", "User Screen");

        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle(e.getMessage());
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    /**
     * handles Cancel button
     */
    private void handleCancelPressed() throws Exception {
        MainController.getInstance().changeScene("../view/LoginScreen.fxml", "Login");
    }
}

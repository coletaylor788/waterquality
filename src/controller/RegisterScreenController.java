package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;

import model.auth.Role;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;

import java.util.Collections;

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

    @FXML
    private void initialize() {
        role.getItems().addAll(generateRoles());
        role.setOnMousePressed((MouseEvent event) -> role.requestFocus());
    }

    /**
     * Creates an observable list to put in the ComboBox
     * @return list with all the roles
     */
    private static ObservableList<Role> generateRoles() {
        Role[] roles = Role.values();
        ObservableList<Role> roleList = FXCollections.observableArrayList();
        Collections.addAll(roleList, roles);
        return roleList;
    }

    @FXML
    private void handleRegisterPressed() {
        try {
            MainController.getInstance().getFacade().getUsers().addUser(usernameField.getText(),
                                passwordField.getText(),
                                firstNameField.getText(),
                                lastNameField.getText(),
                                role.getValue(),
                                emailField.getText());
            MainController.getInstance().getFacade().getUsers().login(usernameField.getText(), passwordField.getText());

            MainController.getInstance().changeScene("../view/Home.fxml", "Home");

        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            MainController.getInstance().showAlertMessage(e.getMessage());
        }
    }

    @FXML
    private void handleCancelPressed() throws Exception {
        MainController.getInstance().changeScene("../view/LoginScreen.fxml", "Login");
    }
}

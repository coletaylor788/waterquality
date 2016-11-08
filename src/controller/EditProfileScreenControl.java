package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.auth.Role;
import model.auth.State;
import model.auth.User;
import javafx.fxml.FXML;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;

import java.util.Collections;


public class EditProfileScreenControl {

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
    private TextField addressField;

    @FXML
    private TextField cityField;

    @FXML
    private ComboBox<State> state;

    @FXML
    private TextField zipCodeField;

    private User user;

    @FXML
    private void initialize() {
        role.getItems().addAll(generateRoles());
        state.getItems().addAll(generateState());
        setDefaultFields();

        role.setOnMousePressed((MouseEvent event) -> role.requestFocus());
        state.setOnMousePressed((MouseEvent event) -> state.requestFocus());
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

    /**
     * Creates an observable list to put in the ComboBox
     * @return list with all the states
     */
    private static ObservableList<State> generateState() {
        State[] states = State.values();
        ObservableList<State> stateList = FXCollections.observableArrayList();
        Collections.addAll(stateList, states);
        return stateList;
    }

    private void setDefaultFields() {
        user = MainController.getInstance().getFacade().getUsers().getCurrentUser();
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        usernameField.setText(user.getUsername());
        role.setValue(user.getRole());
        addressField.setText(user.getAddress());
        cityField.setText(user.getCity());
        state.setValue(user.getState());
        zipCodeField.setText(((Integer) user.getZipCode()).toString());
    }

    @FXML
    private void handleSavePressed() throws Exception {
        try {
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setEmail(emailField.getText());
            if (!passwordField.getText().equals("")) {
                user.setPassword(passwordField.getText());
            }
            user.setRole(role.getValue());
            user.setAddress(addressField.getText());
            user.setCity(cityField.getText());
            user.setState(state.getValue());
            user.setZipCode(Integer.parseInt(zipCodeField.getText()));

            MainController.getInstance().changeScene("../view/Home.fxml", "Home");

        } catch (AuthenticationException | EmptyRequiredFieldException e) {
            MainController.getInstance().showAlertMessage(e.getMessage());
        }
    }

    @FXML
    private void handleCancelPressed() throws Exception {
        MainController.getInstance().changeScene("../view/Home.fxml", "Home");
    }
}

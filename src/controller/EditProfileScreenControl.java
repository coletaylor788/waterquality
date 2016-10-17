package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.auth.Role;
import model.auth.State;
import model.auth.User;
import javafx.fxml.FXML;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;

import java.io.IOException;


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
    private Stage _dialogStage;

    @FXML
    /**
     * populates combo boxes
     */
    private void initialize() {
        role.getItems().addAll(generateRoles());
        state.getItems().addAll(generateState());
        setDefaultFields();
        role.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                role.requestFocus();
            }
        });
        state.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                state.requestFocus();
            }
        });
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

    /**
     * Creates an observable list to put in the ComboBox
     * @return list with all the states
     */
    private static ObservableList generateState() {
        State[] states = State.values();
        ObservableList<State> stateList = FXCollections.observableArrayList();
        for (State state : states) {
            stateList.add(state);
        }
        return stateList;
    }

    public void setDefaultFields() {
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
    /**
     * handles Save button
     */
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
        MainController.getInstance().changeScene("../view/UserScreen.fxml", "User Screen");
    }
}

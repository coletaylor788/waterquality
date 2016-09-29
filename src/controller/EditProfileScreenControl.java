package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    private MainController mainController;
    private Stage _dialogStage;



    @FXML
    private void initialize() {
        user = mainController.getUsersData().getCurrentUser();
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPasswordHash());

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleSavePressed() {
        try {
            

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
}

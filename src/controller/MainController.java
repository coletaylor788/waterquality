package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Facade;
import model.PersistenceManager;

import java.io.IOException;
import java.net.URL;


public class MainController extends Application {

    private Stage primaryStage;
    private Stage dialogStage;
    private PersistenceManager persistenceManager;
    private static MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        stageAssignment(primaryStage);
        mainController = this;
        persistenceManager = new PersistenceManager(Facade.getInstance());
    }

    public Facade getFacade() {
        return Facade.getInstance();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    //sets main stage and assigns stage instance so we can reuse
    private void stageAssignment(Stage primaryStage) {
        //This block of code is needed to change layouts
        //in getResource you put in the path to the next FXML file
        changeScene("../view/LoginScreen.fxml", "Login Screen");
    }

    /**
     * Changes the scene given the scene name (in view)
     *
     * @param scenePath is the path of the fxml file (i.e. ../view/LoginScreen.fxml)
     * @param title is the title of the scene
     */
    public void changeScene(String scenePath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(scenePath));
            Parent screen = loader.load();

            //sets the scene
            Stage primaryStage = getPrimaryStage();
            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(screen));
            primaryStage.show();
        } catch (IOException e) {
            showAlertMessage("Unable to load: " + title, Alert.AlertType.ERROR);
        }
    }

    /**
     * Used to get the Main Controller of the application
     *
     * @return the main controller instance
     */
    public static MainController getInstance() {
        return mainController;
    }

    /**
     * Displays an alert dialog box
     *
     * @param message is the message to display
     * @param alertType is the type of alert box to generate
     */
    public void showAlertMessage(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.initOwner(dialogStage);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }
}

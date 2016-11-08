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


public class MainController extends Application {

    private Stage primaryStage;
    private PersistenceManager persistenceManager;
    private static MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        changeScene("../view/LoginScreen.fxml", "Login");
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
            showAlertMessage("Unable to load: " + title);
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
     */
    public void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Alert.AlertType.ERROR.toString());
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }
}

package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import model.auth.User;
import model.auth.UsersData;
import model.reports.WaterSourceReports;

import java.io.IOException;


public class MainController extends Application {

    private Stage primaryStage;
    private UsersData usersData;
    private WaterSourceReports waterSourceReports;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        usersData = new UsersData();
        stageAssignment(primaryStage);
    }

    public UsersData getUsersData() {
        return usersData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public WaterSourceReports getWaterSourceReports() {
        return waterSourceReports;
    }

    //sets main stage and assigns stage instance so we can reuse
    private void stageAssignment(Stage primaryStage) {
        //This block of code is needed to change layouts
        //in getResource you put in the path to the next FXML file
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("../view/LoginScreen.fxml"));
            BorderPane loginScreen = loader.load();
            LoginScreenController controller = loader.getController();
            controller.setMainController(this);

            //sets the scene
            primaryStage.setTitle("Login Screen");
            primaryStage.setScene(new Scene(loginScreen));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

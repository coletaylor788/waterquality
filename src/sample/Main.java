package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TODO
import model.ApacheShiroAuthenticate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        ApacheShiroAuthenticate auth = new ApacheShiroAuthenticate();

        auth.login("user", "pass");
    }


    public static void main(String[] args) {
        launch(args);
    }
}

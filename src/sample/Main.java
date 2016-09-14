package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.auth.User;
import model.auth.UsersData;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        UsersData.addUser("user", "pass", "John", "Smith", "email");
        System.out.println(UsersData.getCurrentUser());
        System.out.println(UsersData.login("user", "pass").getFirstName());
        System.out.println(UsersData.getCurrentUser().getLastName());

    }


    public static void main(String[] args) {
        launch(args);
    }
}

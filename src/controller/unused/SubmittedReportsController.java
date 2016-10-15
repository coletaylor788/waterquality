package controller.unused;

/**
 * Created by Nkosi Kee on 10/13/2016.
 */
import controller.MainController;
import controller.UserScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.auth.Role;
import model.auth.User;
import model.reports.SourceReport;
import model.reports.WaterSourceReports;
import model.reports.WaterType;

import java.io.IOException;
import java.util.List;

public class SubmittedReportsController {

    private MainController mainController;
    private User user;

    @FXML
    private ListView<?> submittedReports;

    /**
     * sets main controller
     * @param mainController controller controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        submittedReports.getItems().setAll(generateReports());
    }

    /**
     * Generates reports
     * @return an observable list of reports
     */
    private ObservableList generateReports() {
        List<SourceReport> reports =mainController.getFacade().getSourceReports().getSourceReports();
        ObservableList<SourceReport> sourceReports = FXCollections.observableArrayList();
        for (SourceReport report : reports) {
            sourceReports.add(report);
        }
        return sourceReports;
    }

    @FXML
    void handleCancelPressed() {
        user = mainController.getFacade().getUsers().getCurrentUser();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("../view/UserScreen.fxml"));
            BorderPane userScreen = loader.load();
            UserScreenController controller = loader.getController();
            //controller.setMainController(mainController);

            // Sets the scene
            Stage primaryStage = mainController.getPrimaryStage();
            primaryStage.setTitle("User: " + user.getFirstName());
            primaryStage.setScene(new Scene(userScreen));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

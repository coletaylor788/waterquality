package controller.unused;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.auth.User;
import model.reports.Location;
import model.reports.SourceReport;
import model.reports.WaterCondition;
import model.reports.WaterType;

import java.io.IOException;
import java.time.ZoneId;

public class WaterAvailabilityReportController {

    @FXML
    private Label reportNumLabel;

    @FXML
    private Label reportNum;

    @FXML
    private Label dateAndTimeLabel;

    @FXML
    private DatePicker date;

    @FXML
    private TextField time;

    @FXML
    private Label reporterLabel;

    @FXML
    private TextField reportersName;

    @FXML
    private Label lattLabel;

    @FXML
    private Label longLabel;

    @FXML
    private Label waterTypeLabel;

    @FXML
    private Label waterConditionLabel;

    @FXML
    private TextField lattitudeEnter;

    @FXML
    private TextField longitudeEnter;

    @FXML
    private ComboBox<WaterType> waterType;

    @FXML
    private ComboBox<WaterCondition> waterCondition;

    SourceReport sourceReport;
    Location location;

    private MainController mainController;
    private User user;

    /**
     * generates the water types
     * @return observable list of water types
     */
    private static ObservableList generateTypes() {
        WaterType[] types = WaterType.values();
        ObservableList<WaterType> typeList = FXCollections.observableArrayList();
        for (WaterType type : types) {
            typeList.add(type);
        }
        return typeList;
    }

    /**
     * generates water conditions
     * @return observabel list of water conditions
     */
    private static ObservableList generateConditions() {
        WaterCondition[] conditions = WaterCondition.values();
        ObservableList<WaterCondition> conditionList = FXCollections.observableArrayList();
        for (WaterCondition condition : conditions) {
            conditionList.add(condition);
        }
        return conditionList;
    }

    /**
     * autogenerates necessary fields
     */
    private void generateFields() {
        user = mainController.getFacade().getUsers().getCurrentUser();
        String usersName = ""+user.getFirstName()+" "+user.getLastName();
        reportersName.setText(usersName);
        location = new Location(0.0,0.0);
        //sourceReport = new SourceReport(user,location,WaterType.BOTTLED,WaterCondition.POTABLE);
        date.setValue(sourceReport.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        time.setText(sourceReport.getStringTimestamp());
        reportNum.setText(""+sourceReport.getID());
    }

    /**
     * sets the main controller
     * @param mainController controller passed
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        generateFields();
        waterType.getItems().addAll(generateTypes());
        waterCondition.getItems().addAll(generateConditions());
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

    @FXML
    void handleSubmitPressed() {
        user = mainController.getFacade().getUsers().getCurrentUser();
        sourceReport.setCondition(waterCondition.getValue());
        sourceReport.setWaterType(waterType.getValue());
        location = new Location(Double.parseDouble(lattitudeEnter.getText()),
                Double.parseDouble(longitudeEnter.getText()));
        sourceReport.setLocation(location);
        mainController.getFacade().getSourceReports().addSourceReport(sourceReport);
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

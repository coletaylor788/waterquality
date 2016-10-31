package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.reports.Location;
import model.reports.OverallCondition;
import model.reports.PurityReport;
import model.reports.exceptions.LocationOutOfRangeException;

public class PurityReportController {

    @FXML
    private ComboBox<OverallCondition> conditionBox;

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    @FXML
    private TextField virusPPM;

    @FXML
    private TextField contaminantPPM;

    private Stage _dialogStage;

    @FXML
    private void initialize() {
        conditionBox.getItems().addAll(generateOverallConditions());
        conditionBox.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                conditionBox.requestFocus();
            }
        });
    }

    private static ObservableList generateOverallConditions() {
        OverallCondition[] overallConditions = OverallCondition.values();
        ObservableList<OverallCondition> conditionList = FXCollections.observableArrayList();
        for (OverallCondition type: overallConditions) {
            conditionList.add(type);
        }
        return conditionList;
    }

    @FXML
    private void handleSubmitPressed() {
        String message = null;
        try {
            double virus = Double.parseDouble(virusPPM.getText());
            double contaminant = Double.parseDouble(contaminantPPM.getText());

            double lat = Double.parseDouble(latitude.getText());
            double lon = Double.parseDouble(longitude.getText());
            if (lat < -90 || lat > 90) {
                throw new LocationOutOfRangeException("Latitude must be a value between -90 and 90");
            } else if (lon < -180 || lon > 180) {
                throw new LocationOutOfRangeException("Longitude must be a value between 0 and 180");
            }

            Location loc = new Location(lat, lon);

            //System.out.println(MainController.getInstance().getFacade().getUsers().getCurrentUser());
            //System.out.println(MainController.getInstance().getFacade().getUsers().getCurrentUser().getRole());

<<<<<<< HEAD:src/controller/PurityReportController.java
            PurityReport purReport = new PurityReport(MainController.getInstance().getFacade().getUsers().getCurrentUser()
                    ,(OverallCondition) conditionBox.getValue(), virus,purity);
=======
            PurityReport purReport = new PurityReport(MainController.getInstance().getFacade().getUsers()
                    .getCurrentUser(), conditionBox.getValue(), loc, virus, contaminant);
>>>>>>> origin/master:src/controller/WaterPurityReportController.java

            MainController.getInstance().getFacade().getPurityReports().addPurityReport(purReport);
            MainController.getInstance().changeScene("../view/Home.fxml", "Home");
        } catch (NumberFormatException e) {
            message = "Virus PPM, Purity PPM, Longitude, and Latitude must be decimal values";
        } catch (LocationOutOfRangeException e) {
            message = e.getMessage();
        }

        // If there was an error
        if (message != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText(message);
            alert.showAndWait();
        }
    }

    /**
     * handles Cancel button
     */
    @FXML
    private void handleCancelPressed() {
        MainController.getInstance().changeScene("../view/Home.fxml", "Home");
    }

}

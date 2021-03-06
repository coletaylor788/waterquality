package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.exceptions.EmptyRequiredFieldException;
import model.reports.Location;
import model.reports.OverallCondition;
import model.reports.PurityReport;
import model.reports.exceptions.LocationOutOfRangeException;

import java.util.Collections;

public class WaterPurityReportController {

    @FXML
    private ComboBox<OverallCondition> conditionBox;

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    @FXML
    private TextField contaminantPPM;

    @FXML
    private TextField virusPPM;

    @FXML
    private void initialize() {
        conditionBox.getItems().addAll(generateOverallConditions());
        conditionBox.setOnMousePressed((MouseEvent event) -> conditionBox.requestFocus());
    }

    private static ObservableList<OverallCondition> generateOverallConditions() {
        OverallCondition[] overallConditions = OverallCondition.values();
        ObservableList<OverallCondition> conditionList = FXCollections.observableArrayList();
        Collections.addAll(conditionList, overallConditions);
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

            PurityReport purReport = new PurityReport(MainController.getInstance().getFacade().getUsers()
                    .getCurrentUser(), conditionBox.getValue(), loc, virus, contaminant);

            MainController.getInstance().getFacade().getPurityReports().addPurityReport(purReport);
            MainController.getInstance().changeScene("../view/Home.fxml", "Home");
        } catch (NumberFormatException e) {
            message = "Virus PPM, Purity PPM, Longitude, and Latitude must be decimal values";
        } catch (LocationOutOfRangeException | EmptyRequiredFieldException e) {
            message = e.getMessage();
        }

        // If there was an error
        if (message != null) {
            MainController.getInstance().showAlertMessage(message);
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

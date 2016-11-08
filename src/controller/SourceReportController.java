package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;

import model.exceptions.EmptyRequiredFieldException;
import model.reports.Location;
import model.reports.SourceReport;
import model.reports.WaterCondition;
import model.reports.WaterType;
import model.reports.exceptions.LocationOutOfRangeException;

import java.util.Collections;

public class SourceReportController {

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    @FXML
    private ComboBox<WaterType> waterTypes;

    @FXML
    private ComboBox<WaterCondition> waterConditions;

    @FXML
    private void initialize() {
        waterConditions.getItems().addAll(generateWaterConditions());
        waterTypes.getItems().addAll(generateWaterTypes());

        waterConditions.setOnMousePressed((MouseEvent event) -> waterConditions.requestFocus());
        waterTypes.setOnMousePressed((MouseEvent event) -> waterTypes.requestFocus());
    }

    /**
     * Creates an observable list of water types to populate combo box
     * @return list of all water types
     */
    private static ObservableList<WaterType> generateWaterTypes() {
        WaterType[] waterTypes = WaterType.values();
        ObservableList<WaterType> typeList = FXCollections.observableArrayList();
        Collections.addAll(typeList, waterTypes);
        return typeList;
    }

    /**
     * Creates an observable list of water conditions to populate combo box
     * @return list of all water conditions
     */
    private static ObservableList<WaterCondition> generateWaterConditions() {
        WaterCondition[] waterConditions = WaterCondition.values();
        ObservableList<WaterCondition> conditionList = FXCollections.observableArrayList();
        Collections.addAll(conditionList, waterConditions);
        return conditionList;
    }

    /**
     * Submits a new source report
     */
    @FXML
    private void handleSubmitPressed() {
        String message = null;
        try {
            double lat = Double.parseDouble(latitude.getText());
            double lon = Double.parseDouble(longitude.getText());
            if (lat < -90 || lat > 90) {
                throw new LocationOutOfRangeException("Latitude must be a value between -90 and 90");
            } else if (lon < -180 || lon > 180) {
                throw new LocationOutOfRangeException("Longitude must be a value between 0 and 180");
            }

            Location loc = new Location(lat, lon);

            SourceReport srcReport = new SourceReport(
                    MainController.getInstance().getFacade().getUsers().getCurrentUser(),
                    loc, waterTypes.getValue(), waterConditions.getValue());

            MainController.getInstance().getFacade().getSourceReports().addSourceReport(srcReport);

            MainController.getInstance().changeScene("../view/Home.fxml", "Home");
        } catch (NumberFormatException e) {
            message = "Latitude and Longitude values must be decimal values";
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

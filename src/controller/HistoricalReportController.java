package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.auth.exceptions.AuthenticationException;
import model.exceptions.EmptyRequiredFieldException;
import model.reports.PurityReportGraph;

import java.util.Map;

public class HistoricalReportController {

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    @FXML
    private TextField year;

    @FXML
    private LineChart<String, Number> graph;

    @FXML
    private ComboBox<String> ppm;

    private Map<String, Double> coordinates;
    XYChart.Series series;

    private double lat;
    private double lon;
    private boolean isVirusPPM;


    @FXML
    /**
     * populates combo box
     */
    private void initialize() {
        ppm.getItems().addAll(generatePPM());
        graph.setVisible(false);
    }

    /**
     * Creates list to put combo box
     * @return list for combobox
     */
    private ObservableList<String> generatePPM() {
        ObservableList<String> strList = FXCollections.observableArrayList();
        strList.add("Virus PPM");
        strList.add("Containment PPM");
        return strList;
    }

    private void setGraph(double lat, double lon, boolean isVirusPPM) throws EmptyRequiredFieldException {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("PPM");

        graph = new LineChart<String, Number>(xAxis, yAxis);
        //XYChart.Series series = new XYChart.Series();

        PurityReportGraph coordSetup = new PurityReportGraph(lat, lon, isVirusPPM);
        coordinates = coordSetup.getCoordinates();

        for (Map.Entry<String, Double> entry : coordinates.entrySet()) {
            XYChart.Data data = new XYChart.Data(entry.getKey(), entry.getValue());
            series.getData().add(data);
        }

        graph.getData().add(series);

    }

    private void handleEnterPressed() {
        try {
            lat = Double.parseDouble(latitude.getCharacters().toString());
            lon = Double.parseDouble(longitude.getCharacters().toString());
            isVirusPPM = true;
            if (ppm.getValue().contains("Containment")) {
                isVirusPPM = false;
            }
            setGraph(lat, lon, isVirusPPM);
            graph.setVisible(true);
        } catch (EmptyRequiredFieldException | NumberFormatException e) {
            MainController.getInstance().showAlertMessage(e.getMessage(), Alert.AlertType.ERROR);
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

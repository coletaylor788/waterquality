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
    NumberAxis xAxis;

    @FXML
    NumberAxis yAxis;

    @FXML
    private LineChart<Number, Number> graph;// = new LineChart<>(xAxis, yAxis);

    @FXML
    private ComboBox<String> ppm;

    @FXML
    /**
     * populates combo box
     */
    private void initialize() {
        ppm.getItems().addAll(generatePPM());
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(12);
        xAxis.setTickUnit(1);
        //graph.setVisible(false);
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

    private void setGraph(int year, double lat, double lon, boolean isVirusPPM) throws EmptyRequiredFieldException {
        xAxis.setLabel("Month");
        yAxis.setLabel("PPM");
        if (isVirusPPM) {
            graph.setTitle("Virus PPM");
        } else {
            graph.setTitle("Contaminant PPM");
        }

        graph.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series();

        PurityReportGraph coordSetup = new PurityReportGraph(year, lat, lon, isVirusPPM);
        Map<String, Double> coordinates = coordSetup.getCoordinates();

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        boolean data = false;
        for (int i = 0; i < months.length; i++) {
            if (coordinates.containsKey(months[i])) {
                series.getData().add(new XYChart.Data<>(
                        i + 1, coordinates.get(months[i])));
                data = true;
            }
        }

        if (data) {
            graph.getData().add(series);
        }

    }

    @FXML
    private void handleEnterPressed() {
        boolean cont = true;
        int year = 0;
        try {
            year = Integer.parseInt(this.year.getText());
        } catch (NumberFormatException e) {
            MainController.getInstance().showAlertMessage("Please enter a valid year", Alert.AlertType.ERROR);
            cont = false;
        }

        if (cont) {
            try {
                double lat = Double.parseDouble(latitude.getCharacters().toString());
                double lon = Double.parseDouble(longitude.getCharacters().toString());

                boolean isVirusPPM = true;
                if (ppm.getValue().contains("Containment")) {
                    isVirusPPM = false;
                }
                setGraph(year, lat, lon, isVirusPPM);
                graph.setVisible(true);
            } catch (EmptyRequiredFieldException | NumberFormatException e) {
                MainController.getInstance().showAlertMessage(e.getMessage(), Alert.AlertType.ERROR);
            }
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

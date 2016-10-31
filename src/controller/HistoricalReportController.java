package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class HistoricalReportController {

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    @FXML
    private TextField year;

    @FXML
    private LineChart<Integer, Integer> graph;

    @FXML
    private ComboBox<String> ppm;



    @FXML
    /**
     * populates combo box
     */
    private void initialize() {
        ppm.getItems().addAll(generatePPM());
    }


    private ObservableList<String> generatePPM() {
        ObservableList<String> strList = FXCollections.observableArrayList();
        strList.add("Virus PPM");
        strList.add("Containment PPM");
        return strList;
    }

    /**
     * handles Cancel button
     */
    @FXML
    private void handleCancelPressed() {
        MainController.getInstance().changeScene("../view/Home.fxml", "Home");
    }
}

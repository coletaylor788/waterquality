package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class WaterPurityReportController {

    @FXML
    private FlowPane Pane;

    @FXML
    private DatePicker DatePick;

    @FXML
    private ChoiceBox<?> OverallCondition;

    @FXML
    private TextField Lattitude;

    @FXML
    private TextField Longitude;

}
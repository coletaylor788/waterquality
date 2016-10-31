package controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HistoricalReportController {

    @FXML
    TextField latitude;

    @FXML
    TextField longitude;

    @FXML
    TextField year;


    /**
     * handles Cancel button
     */
    @FXML
    private void handleCancelPressed() {
        MainController.getInstance().changeScene("../view/Home.fxml", "Home");
    }
}

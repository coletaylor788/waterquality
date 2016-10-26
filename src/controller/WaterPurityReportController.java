import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.exceptions.EmptyRequiredFieldException;
import model.reports.*;
import model.reports.exceptions.LocationOutOfRangeException;
import java.util.ArrayList;

public class WaterPurityReportController {

    @FXML
    private ComboBox<?> conditionBox;

    @FXML
    private TextField purittyPPM;

    @FXML
    private TextField virusPPM;

    private Stage _dialogStage;

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
            double virus = Double.parseDouble(virusPPM .getText());
            double purity = Double.parseDouble(purittyPPM.getText());


            PurityReport purReport = new PurityReport(MainController.getInstance().getFacade().getUsers()
                    .getCurrentUser(),(OverallCondition) conditionBox.getValue(), virus,purity);

            MainController.getInstance().getFacade().getSourceReports().getSourceReports().get(1).addPurityReport(purReport);
            MainController.getInstance().changeScene("../view/Home.fxml", "Home");
        } catch (NumberFormatException e) {
            message = "Virus PPM and Purity PPM must be decimal values";
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

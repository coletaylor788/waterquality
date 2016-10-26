import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class WaterPurityReportController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button submitReportButton;

    @FXML
    private ComboBox<?> conditionBox;

    @FXML
    private TextField purittyPPM;

    @FXML
    private TextField virusPPM;

}

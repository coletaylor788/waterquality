package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.MainController;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;

/**
 * Handles saving and loading data for the application
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class PersistenceManager {

    private Facade model;

    /**
     * Creates a PersistenceManager
     *
     * @param model is the model to save/load
     */
    public PersistenceManager(Facade model) {
        this.model = model;
    }

    public void saveToJson(File file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            String str = gson.toJson(model);
            pw.println(str);
            pw.close();
        } catch (IOException e) {
            MainController.getInstance().showAlertMessage(
                    "Exception working with Json Save File", Alert.AlertType.ERROR);
        }
    }

    public void loadFromJsonfile(File file) {
        String ct = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Type modelType = new TypeToken<Facade>(){}.getType();
            Gson gson = new Gson();

            ct = br.readLine();

            model = gson.fromJson(ct, modelType);
            Facade.setInstance(model);

            br.close();
        } catch (IOException e) {
            MainController.getInstance().showAlertMessage(
                    "Exception working with Json load file", Alert.AlertType.ERROR);
        }
    }
}

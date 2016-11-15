package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.MainController;

import java.io.*;
import java.lang.reflect.Type;

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

    /**
     * Saves the model
     * @param file the file to save to
     */
    public void saveToJson(File file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            String str = gson.toJson(model);
            pw.println(str);
            pw.close();
        } catch (IOException e) {
            MainController.getInstance().showAlertMessage(
                    "Exception working with Json Save File");
        }
    }

    /**
     * Loads the model from a file
     * @param file the file to load from
     */
    public void loadFromJsonfile(File file) {
        String ct;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Type modelType = new TypeToken<Facade>(){}.getType();
            Gson gson = new Gson();

            ct = br.readLine();

            model = gson.fromJson(ct, modelType);
            Facade.setInstance(model);

            br.close();
        } catch (IOException e) {
            MainController.getInstance().showAlertMessage(
                    "Exception working with Json load file");
        }
    }
}

package model.reports;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import model.auth.User;

import java.util.Date;

/**
 * Created by cole on 9/30/16.
 */
public class SourceReport {

    private ObjectProperty<Date> timestamp = new SimpleObjectProperty<>();
    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<User> reportedUsed = new SimpleObjectProperty<>();

}

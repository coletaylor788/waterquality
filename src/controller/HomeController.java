package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Facade;
import model.auth.Role;
import model.auth.User;
import model.reports.SourceReport;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Main Screen
 *
 * @author Cole Taylor
 * @version 1.0
 */
public class HomeController implements Initializable, MapComponentInitializedListener{

    @FXML
    private GoogleMapView mapView;
    private GoogleMap map;

    @FXML
    Button submitSourceReport;

    @FXML
    Button submitPurityReport;

    @FXML
    Button viewPurityReport;

    @FXML
    Button historicalReport;

    /**
     * Initializes the Map Listener to communicate with Google Maps
     * @param url is the url to initialize the map with
     * @param rb is the resource bundle to initialize the map with
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setVisibleFields();
        mapView.addMapInializedListener(this);
    }

    private void setVisibleFields() {
        User currUser = MainController.getInstance().getFacade().getUsers().getCurrentUser();
        if (currUser.getRole().equals(Role.USER)) {
            submitSourceReport.setVisible(true);
            submitPurityReport.setVisible(false);
            viewPurityReport.setVisible(false);
            historicalReport.setVisible(false);
        } else if (currUser.getRole().equals(Role.WORKER)) {
            submitSourceReport.setVisible(true);
            submitPurityReport.setVisible(true);
            viewPurityReport.setVisible(false);
            historicalReport.setVisible(false);
        } else if (currUser.getRole().equals(Role.MANAGER)) {
            submitSourceReport.setVisible(true);
            submitPurityReport.setVisible(true);
            viewPurityReport.setVisible(true);
            historicalReport.setVisible(true);
        } else { // Admin
            submitSourceReport.setVisible(false);
            submitPurityReport.setVisible(false);
            viewPurityReport.setVisible(false);
        }
    }

    /**
     * Initializes the map and displays the water source reports
     * as markers on the map
     */
    @Override
    public void mapInitialized() {
        List<SourceReport> sourceReports = MainController.getInstance().getFacade()
                .getSourceReports().getSourceReports();

        // Center map at Atlanta
        double atlanta_lat = 33.7;
        double atlanta_long = -84.39;

        LatLong center = new LatLong(atlanta_lat, atlanta_long);

        MapOptions options = new MapOptions();
        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapView.createMap(options);

        // For each source report, create a marker on the map
        for (SourceReport report: sourceReports) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(report.getLocation().getLatitude(), report.getLocation().getLongitude());

            markerOptions.position(loc)
                    .visible(Boolean.TRUE)
                    .title(report.getTitle());

            Marker marker = new Marker(markerOptions);

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(report.getDescription());
                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);
                    });

            map.addMarker(marker);
        }
    }

    @FXML
    private void handleLogoutPressed() throws Exception {
        Facade.getInstance().getUsers().logout();
        MainController.getInstance().changeScene("../view/LoginScreen.fxml", "Login");
    }

    @FXML
    private void handleEditPressed() {
        MainController.getInstance().changeScene("../view/EditProfileScreen.fxml", "Edit Profile");
    }

    @FXML
    private void handleSubmitSourceReportPressed() {
        MainController.getInstance().changeScene("../view/WaterSourceReport.fxml", "Water Source Report");
    }

    /**
     * Handles submitting a purity report
     */
    @FXML
    public void handleSubmitPurityReportPressed() {
        MainController.getInstance().changeScene("../view/WaterPurityReport.fxml", "Water Purity Report");
    }

    /**
     * Handles viewing a purity report
     */
    @FXML
    public void handleViewPurityReportPressed() {
        MainController.getInstance().changeScene("../view/ViewWaterPurityReport.fxml", "Water Purity Reports");
    }

    /**
     * Handles changing to historical report
     */
    @FXML
    public void handleHistoricalReportPressed() {
        MainController.getInstance().changeScene("../view/HistoricalReport.fxml", "Historical Report");
        Facade.getInstance().getPurityReports().createSampleReports();
    }
}

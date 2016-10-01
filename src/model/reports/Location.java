package model.reports;

/**
 * Created by cole on 9/30/16.
 */
public class Location {

    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(int latDegrees, int latMinutes, double latSeconds,
                    int longDegrees, int longMinutes, double longSeconds) {
        this((latSeconds / 60) + (latMinutes / 60.0) + (latDegrees),
                (longSeconds / 60) + (longMinutes / 60.0) + (longDegrees));
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}

package model.reports;

import java.io.Serializable;

/**
 * Created by cole on 9/30/16.
 */
public class Location implements Serializable {

    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public String toString() {
        String degree = "\u00b0";
        return "Lat: " + latitude + degree + ", Long: " + longitude + degree;
    }

    /**
     * Checks if two Location objects are equal
     *
     * @param other is the other object to check
     * @return true if lat and long are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (!(other instanceof Location)) {
            return false;
        } else {
            Location otherLoc = (Location) other;
            return this.latitude == otherLoc.latitude && this.longitude == otherLoc.longitude;
        }
    }
}

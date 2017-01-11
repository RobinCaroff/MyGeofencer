package com.robincaroff.mygeofencer.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Model used to hold geofence data.
 */


public class MyGeofence {
    private LatLng location;
    private String name;

    public MyGeofence(LatLng location, String name) {
        this.location = location;
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}

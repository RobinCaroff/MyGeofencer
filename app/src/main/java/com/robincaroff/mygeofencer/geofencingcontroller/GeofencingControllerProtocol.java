package com.robincaroff.mygeofencer.geofencingcontroller;

import com.robincaroff.mygeofencer.models.MyGeofence;

import java.util.List;

/**
 * Defines the geofences controller protocol
 */


public interface GeofencingControllerProtocol {
    void connect();
    void disconnect();
    void updateGeofenceList(List<MyGeofence> geofences);
}

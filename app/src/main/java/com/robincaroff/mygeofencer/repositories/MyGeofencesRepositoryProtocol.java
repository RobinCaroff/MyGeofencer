package com.robincaroff.mygeofencer.repositories;

import com.robincaroff.mygeofencer.models.MyGeofence;

import java.util.List;

/**
 * Defines Geofence repository protocol
 */

public interface MyGeofencesRepositoryProtocol {
    void deleteGeofence(MyGeofence geofence);
    List<MyGeofence> getGeofences();
    void saveGeofence(MyGeofence geofence);
    void updateGeofence(MyGeofence geofence);
}

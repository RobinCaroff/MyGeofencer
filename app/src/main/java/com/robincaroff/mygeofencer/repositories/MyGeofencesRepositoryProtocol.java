package com.robincaroff.mygeofencer.repositories;

import com.robincaroff.mygeofencer.models.MyGeofence;

import java.util.List;

/**
 * Defines Geofence repository protocol
 */


public interface MyGeofencesRepositoryProtocol {
    List<MyGeofence> getGeofences();
    void saveGeofence(MyGeofence geofence);
}

package com.robincaroff.mygeofencer.utils;

/**
 * Defines app constants
 */


public class Constants {

    public static final String PACKAGE_NAME = "com.robincaroff.mygeofencer";

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234;
    public static final int ADD_GEOFENCE_REQUEST = 9001;
    public static final int EDIT_GEOFENCE_REQUEST = 9002;

    public static final String MYGEOFENCE_EXTRA = PACKAGE_NAME + ".MYGEOFENCE_EXTRA";
    public static final String NOTIFICATION_EXTRA = PACKAGE_NAME + ".NOTIFICATION_EXTRA";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 500;
}

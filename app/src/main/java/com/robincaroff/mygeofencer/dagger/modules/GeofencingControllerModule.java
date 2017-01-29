package com.robincaroff.mygeofencer.dagger.modules;

import android.content.Context;

import com.robincaroff.mygeofencer.geofencingcontroller.GeofencingController;
import com.robincaroff.mygeofencer.geofencingcontroller.GeofencingControllerProtocol;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides GeofencingControllerProtocol
 */

@Module
public class GeofencingControllerModule {

    public GeofencingControllerModule() {}

    @Provides
    @Singleton
    GeofencingControllerProtocol provideGeofencingController(Context context) {
        return new GeofencingController(context);
    }
}

package com.robincaroff.mygeofencer;

import android.app.Application;

import com.robincaroff.mygeofencer.dagger.components.DaggerMyGeofencerComponent;
import com.robincaroff.mygeofencer.dagger.components.MyGeofencerComponent;
import com.robincaroff.mygeofencer.dagger.modules.ContextModule;
import com.robincaroff.mygeofencer.dagger.modules.GeofencingControllerModule;
import com.robincaroff.mygeofencer.dagger.modules.MyGeofenceRepositoryModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Application class used to create the app's components
 */


public class MyGeofencerApplication extends Application {
    protected static Application application;

    private MyGeofencerComponent myGeofencerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        // Dagger%COMPONENT_NAME%
        myGeofencerComponent = DaggerMyGeofencerComponent.builder()
                .contextModule(new ContextModule(this))
                .myGeofenceRepositoryModule(new MyGeofenceRepositoryModule())
                .geofencingControllerModule(new GeofencingControllerModule())
                .build();
    }

    public static Application app() {
        return application;
    }

    public MyGeofencerComponent getMyGeofencerComponent() {
        return myGeofencerComponent;
    }
}

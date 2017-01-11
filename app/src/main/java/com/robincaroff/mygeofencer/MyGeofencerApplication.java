package com.robincaroff.mygeofencer;

import android.app.Application;


import com.robincaroff.mygeofencer.dagger.components.DaggerMyGeofenceRepositoryComponent;
import com.robincaroff.mygeofencer.dagger.components.MyGeofenceRepositoryComponent;
import com.robincaroff.mygeofencer.dagger.modules.ContextModule;
import com.robincaroff.mygeofencer.dagger.modules.MyGeofenceRepositoryModule;

/**
 * Application class used to create the app's components
 */


public class MyGeofencerApplication extends Application {
    protected static Application application;

    MyGeofenceRepositoryComponent myGeofenceRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        // Dagger%COMPONENT_NAME%
        myGeofenceRepositoryComponent = DaggerMyGeofenceRepositoryComponent.builder()
                .contextModule(new ContextModule(this))
                .myGeofenceRepositoryModule(new MyGeofenceRepositoryModule())
                .build();
    }

    public static Application app() {
        return application;
    }

    public MyGeofenceRepositoryComponent getMyGeofenceRepositoryComponent() {
        return myGeofenceRepositoryComponent;
    }
}

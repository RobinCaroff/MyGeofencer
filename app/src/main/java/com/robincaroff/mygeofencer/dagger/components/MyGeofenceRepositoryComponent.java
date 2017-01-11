package com.robincaroff.mygeofencer.dagger.components;

import com.robincaroff.mygeofencer.AddGeofenceActivity;
import com.robincaroff.mygeofencer.MainActivity;
import com.robincaroff.mygeofencer.dagger.modules.ContextModule;
import com.robincaroff.mygeofencer.dagger.modules.MyGeofenceRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules={ContextModule.class, MyGeofenceRepositoryModule.class})
public interface MyGeofenceRepositoryComponent {

    void inject(AddGeofenceActivity addGeofenceActivity);
    void inject(MainActivity addGeofenceActivity);
}

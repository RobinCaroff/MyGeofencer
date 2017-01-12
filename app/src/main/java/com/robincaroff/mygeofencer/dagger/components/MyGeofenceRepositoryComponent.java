package com.robincaroff.mygeofencer.dagger.components;

import com.robincaroff.mygeofencer.activities.AddGeofenceActivity;
import com.robincaroff.mygeofencer.activities.EditGeofenceActivity;
import com.robincaroff.mygeofencer.activities.MainActivity;
import com.robincaroff.mygeofencer.dagger.modules.ContextModule;
import com.robincaroff.mygeofencer.dagger.modules.MyGeofenceRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules={ContextModule.class, MyGeofenceRepositoryModule.class})
public interface MyGeofenceRepositoryComponent {

    void inject(AddGeofenceActivity addGeofenceActivity);
    void inject(MainActivity mainActivity);
    void inject(EditGeofenceActivity editGeofenceActivity);
}

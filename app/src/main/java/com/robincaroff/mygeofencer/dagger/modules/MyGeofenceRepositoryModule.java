package com.robincaroff.mygeofencer.dagger.modules;

import android.content.Context;

import com.robincaroff.mygeofencer.repositories.InternalStorageMyGeofencesRepository;
import com.robincaroff.mygeofencer.repositories.MyGeofencesRepositoryProtocol;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides MyGeofenceRepositories
 */

@Module
public class MyGeofenceRepositoryModule {

    public MyGeofenceRepositoryModule() {}

    @Provides
    @Singleton
    MyGeofencesRepositoryProtocol provideMyGeofencesRepositoryProtocol(Context context) {
        return new InternalStorageMyGeofencesRepository(context);
    }
}

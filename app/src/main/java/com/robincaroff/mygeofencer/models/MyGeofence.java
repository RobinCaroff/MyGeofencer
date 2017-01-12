package com.robincaroff.mygeofencer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Model used to hold geofence data.
 */


public class MyGeofence implements Parcelable {
    private LatLng location;
    private String name;

    public MyGeofence(LatLng location, String name) {
        this.location = location;
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof MyGeofence)) return false;
        MyGeofence objLayerMetadata = (MyGeofence)obj;
        if (!objLayerMetadata.getName().equals(this.name)) return false;
        if (!objLayerMetadata.getLocation().equals(this.location)) return false;
        return true;
    }

    protected MyGeofence(Parcel in) {
        location = (LatLng) in.readValue(LatLng.class.getClassLoader());
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(location);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MyGeofence> CREATOR = new Parcelable.Creator<MyGeofence>() {
        @Override
        public MyGeofence createFromParcel(Parcel in) {
            return new MyGeofence(in);
        }

        @Override
        public MyGeofence[] newArray(int size) {
            return new MyGeofence[size];
        }
    };
}

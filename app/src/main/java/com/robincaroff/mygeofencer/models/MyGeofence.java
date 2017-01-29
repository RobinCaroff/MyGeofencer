package com.robincaroff.mygeofencer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Model used to hold geofence data.
 */


public class MyGeofence implements Parcelable {
    private String uuid;
    private LatLng location;
    private String name;
    private boolean enabled;

    public MyGeofence(String uuid, LatLng location, String name, boolean enabled) {
        this.uuid = uuid.trim();
        this.location = location;
        this.name = name;
        this.enabled = enabled;
    }

    public MyGeofence(String uuid, LatLng location, String name) {
        this.uuid = uuid.trim();
        this.location = location;
        this.name = name;
        this.enabled = true;
    }

    public String getUuid() {
        return uuid;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof MyGeofence)) return false;
        MyGeofence objLayerMetadata = (MyGeofence)obj;
        if (!objLayerMetadata.getUuid().equals(this.uuid)) return false;
        if (!objLayerMetadata.getName().equals(this.name)) return false;
        if (!objLayerMetadata.getLocation().equals(this.location)) return false;
        return true;
    }

    protected MyGeofence(Parcel in) {
        uuid = in.readString();
        location = (LatLng) in.readValue(LatLng.class.getClassLoader());
        name = in.readString();
        enabled = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeValue(location);
        dest.writeString(name);
        dest.writeByte((byte) (enabled ? 0x01 : 0x00));
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

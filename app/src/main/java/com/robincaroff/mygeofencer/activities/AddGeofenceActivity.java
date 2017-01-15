package com.robincaroff.mygeofencer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.robincaroff.mygeofencer.MyGeofencerApplication;
import com.robincaroff.mygeofencer.R;
import com.robincaroff.mygeofencer.models.MyGeofence;
import com.robincaroff.mygeofencer.repositories.MyGeofencesRepositoryProtocol;

import java.util.UUID;

import javax.inject.Inject;

/**
 * Activity used to add a location
 */


public class AddGeofenceActivity extends AppCompatActivity {

    private static int PLACE_PICKER_REQUEST = 1;

    private LatLng location;

    private EditText title;
    private Button addButton;

    @Inject MyGeofencesRepositoryProtocol myGeofencesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geofence);

        ((MyGeofencerApplication) getApplication()).getMyGeofencerComponent().inject(this);

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        title = (EditText) findViewById(R.id.title);
        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMyGeofence();
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                location = place.getLatLng();
            }
        }
    }

    private MyGeofence createMyGeofence() {
        MyGeofence geofence = null;

        if(location != null) {
            String name = title.getText().toString();
            if(name != null && !name.isEmpty()) {
                String uuid = UUID.randomUUID().toString();
                geofence = new MyGeofence(uuid, location, name);
                myGeofencesRepository.saveGeofence(geofence);
            } else {
                Toast.makeText(this, getString(R.string.name_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.location_error), Toast.LENGTH_SHORT).show();
        }

        return geofence;
    }
}

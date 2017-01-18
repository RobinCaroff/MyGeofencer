package com.robincaroff.mygeofencer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.robincaroff.mygeofencer.MyGeofencerApplication;
import com.robincaroff.mygeofencer.R;
import com.robincaroff.mygeofencer.models.MyGeofence;
import com.robincaroff.mygeofencer.repositories.MyGeofencesRepositoryProtocol;

import javax.inject.Inject;

import static com.robincaroff.mygeofencer.utils.Constants.MYGEOFENCE_EXTRA;

/**
 * Activity used to edit a geofence
 */


public class EditGeofenceActivity extends AppCompatActivity {

    private MyGeofence myGeofence;

    @Inject
    MyGeofencesRepositoryProtocol myGeofencesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        myGeofence = intent.getParcelableExtra(MYGEOFENCE_EXTRA);
        if(myGeofence == null) {
            Log.e(EditGeofenceActivity.class.getSimpleName(), "Intent extra does not contain a MyGeofence object");
            finish();
        }

        ((MyGeofencerApplication) getApplication()).getMyGeofencerComponent().inject(this);

        setContentView(R.layout.activity_edit_geofence);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(myGeofence.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.geofence_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            deleteGeofence();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteGeofence() {
        myGeofencesRepository.deleteGeofence(myGeofence);
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }
}

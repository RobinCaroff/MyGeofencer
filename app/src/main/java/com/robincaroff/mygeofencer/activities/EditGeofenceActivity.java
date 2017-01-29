package com.robincaroff.mygeofencer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

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

    private Toolbar toolbar;

    private Switch monitorSwitch;
    private EditText nameEditText;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_edit_geofence);
        nameEditText = (EditText) findViewById(R.id.name);
        nameEditText.setText(myGeofence.getName());

        monitorSwitch = (Switch) findViewById(R.id.monitor_switch);
        monitorSwitch.setChecked(myGeofence.isEnabled());
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
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String newName = nameEditText.getText().toString();
        if(newName == null || newName.isEmpty()) {
            nameEditText.setHint(R.string.geofence_title_hint);
            return;
        }

        if(!newName.equals(myGeofence.getName()) || monitorSwitch.isChecked() != myGeofence.isEnabled()) {
            MyGeofence newGeofence = new MyGeofence(myGeofence.getUuid(),
                    myGeofence.getLocation(),
                    newName,
                    monitorSwitch.isChecked());
            myGeofencesRepository.updateGeofence(newGeofence);

            Intent result = new Intent();
            setResult(RESULT_OK, result);
            finish();
            return;
        }

        super.onBackPressed();
    }

    private void deleteGeofence() {
        myGeofencesRepository.deleteGeofence(myGeofence);
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }
}

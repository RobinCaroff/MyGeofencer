package com.robincaroff.mygeofencer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.robincaroff.mygeofencer.MyGeofencerApplication;
import com.robincaroff.mygeofencer.R;
import com.robincaroff.mygeofencer.adapters.MyGeofencesAdapter;
import com.robincaroff.mygeofencer.geofencingcontroller.GeofencingControllerProtocol;
import com.robincaroff.mygeofencer.models.MyGeofence;
import com.robincaroff.mygeofencer.repositories.MyGeofencesRepositoryProtocol;

import java.util.List;

import javax.inject.Inject;

import static com.robincaroff.mygeofencer.utils.Constants.ADD_GEOFENCE_REQUEST;
import static com.robincaroff.mygeofencer.utils.Constants.EDIT_GEOFENCE_REQUEST;
import static com.robincaroff.mygeofencer.utils.Constants.MYGEOFENCE_EXTRA;
import static com.robincaroff.mygeofencer.utils.Constants.NOTIFICATION_EXTRA;

public class MainActivity extends AppCompatActivity implements MyGeofencesAdapter.MyGeofencesAdapterDelegate {

    private static String TAG = MainActivity.class.getSimpleName();

    @Inject MyGeofencesRepositoryProtocol myGeofencesRepository;
    @Inject GeofencingControllerProtocol geofencingController;

    private RecyclerView recyclerView;
    private TextView noGeofenceMessage;
    private MyGeofencesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyGeofencerApplication) getApplication()).getMyGeofencerComponent().inject(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddGeofenceActivity();
            }
        });

        noGeofenceMessage = (TextView) findViewById(R.id.no_geofence_message);

        recyclerView = (RecyclerView) findViewById(R.id.geofences_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        updateView();

        // Check if opened from a notification
        Intent intent = getIntent();
        String notificationDetails = intent.getStringExtra(NOTIFICATION_EXTRA);
        if(notificationDetails == null) {
            updateGeofences();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        geofencingController.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        geofencingController.disconnect();
    }

    private void goToAddGeofenceActivity() {
        Intent intent = new Intent(this, AddGeofenceActivity.class);
        startActivityForResult(intent, ADD_GEOFENCE_REQUEST);
    }

    @Override
    public void itemClicked(int position) {
        MyGeofence geofence = adapter.getDataset().get(position);
        Intent intent = new Intent(this, EditGeofenceActivity.class);
        intent.putExtra(MYGEOFENCE_EXTRA, geofence);
        startActivityForResult(intent, EDIT_GEOFENCE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == ADD_GEOFENCE_REQUEST) {
                MyGeofence myGeofence = data.getParcelableExtra(MYGEOFENCE_EXTRA);
                if(myGeofence != null) {
                    updateGeofences();
                } else {
                    Log.e(TAG, "Could not get the created geofence result");
                }
            } else if(requestCode == EDIT_GEOFENCE_REQUEST) {
                updateGeofences();
            }
        }
    }

    private void updateGeofences() {
        updateView();
        geofencingController.updateGeofenceList(myGeofencesRepository.getGeofences());
    }

    private void updateView() {
        List<MyGeofence> geofences = myGeofencesRepository.getGeofences();
        adapter = new MyGeofencesAdapter(geofences, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.INVISIBLE);
        noGeofenceMessage.setVisibility(adapter.getItemCount() > 0 ? View.INVISIBLE : View.VISIBLE);
    }
}

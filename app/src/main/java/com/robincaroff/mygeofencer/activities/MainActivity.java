package com.robincaroff.mygeofencer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.robincaroff.mygeofencer.MyGeofencerApplication;
import com.robincaroff.mygeofencer.R;
import com.robincaroff.mygeofencer.adapters.MyGeofencesAdapter;
import com.robincaroff.mygeofencer.models.MyGeofence;
import com.robincaroff.mygeofencer.repositories.MyGeofencesRepositoryProtocol;

import java.util.List;

import javax.inject.Inject;

import static com.robincaroff.mygeofencer.utils.Constants.MYGEOFENCE_EXTRA;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MyGeofencesAdapter.MyGeofencesAdapterDelegate {

    protected static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient googleApiClient;

    @Inject MyGeofencesRepositoryProtocol myGeofencesRepository;

    private RecyclerView recyclerView;
    private TextView noGeofenceMessage;
    private MyGeofencesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyGeofencerApplication) getApplication()).getMyGeofenceRepositoryComponent().inject(this);

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

        buildGoogleApiClient();

        noGeofenceMessage = (TextView) findViewById(R.id.no_geofence_message);

        recyclerView = (RecyclerView) findViewById(R.id.geofences_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<MyGeofence> geofences = myGeofencesRepository.getGeofences();
        adapter = new MyGeofencesAdapter(geofences, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.INVISIBLE);
        noGeofenceMessage.setVisibility(adapter.getItemCount() > 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    private void goToAddGeofenceActivity() {
        Intent intent = new Intent(this, AddGeofenceActivity.class);
        startActivity(intent);
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        Log.i(TAG, "Connection suspended");

        // onConnected() will be called again automatically when the service reconnects
    }

    @Override
    public void itemClicked(int position) {
        MyGeofence geofence = adapter.getDataset().get(position);
        Intent intent = new Intent(this, EditGeofenceActivity.class);
        intent.putExtra(MYGEOFENCE_EXTRA, geofence);
        startActivity(intent);
    }
}

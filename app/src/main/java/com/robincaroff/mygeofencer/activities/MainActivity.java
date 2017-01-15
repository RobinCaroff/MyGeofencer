package com.robincaroff.mygeofencer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import static com.robincaroff.mygeofencer.utils.Constants.MYGEOFENCE_EXTRA;

public class MainActivity extends AppCompatActivity implements MyGeofencesAdapter.MyGeofencesAdapterDelegate {

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        geofencingController.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<MyGeofence> geofences = myGeofencesRepository.getGeofences();
        adapter = new MyGeofencesAdapter(geofences, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.INVISIBLE);
        noGeofenceMessage.setVisibility(adapter.getItemCount() > 0 ? View.INVISIBLE : View.VISIBLE);

        geofencingController.updateGeofenceList(geofences);
    }

    @Override
    protected void onStop() {
        super.onStop();
        geofencingController.disconnect();
    }

    private void goToAddGeofenceActivity() {
        Intent intent = new Intent(this, AddGeofenceActivity.class);
        startActivity(intent);
    }

    @Override
    public void itemClicked(int position) {
        MyGeofence geofence = adapter.getDataset().get(position);
        Intent intent = new Intent(this, EditGeofenceActivity.class);
        intent.putExtra(MYGEOFENCE_EXTRA, geofence);
        startActivity(intent);
    }
}

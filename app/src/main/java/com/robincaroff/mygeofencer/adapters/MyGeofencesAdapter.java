package com.robincaroff.mygeofencer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robincaroff.mygeofencer.R;
import com.robincaroff.mygeofencer.models.MyGeofence;

import java.util.List;

/**
 * Adapter for MyGeofence itmes
 */


public class MyGeofencesAdapter extends RecyclerView.Adapter<MyGeofencesAdapter.ViewHolder> {

    private List<MyGeofence> dataset;

    public MyGeofencesAdapter(List<MyGeofence> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mygeofence_recylcyerview_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyGeofence geofence = dataset.get(position);

        holder.name.setText(geofence.getName().toString());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mRootView;
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            mRootView = itemView;

            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}

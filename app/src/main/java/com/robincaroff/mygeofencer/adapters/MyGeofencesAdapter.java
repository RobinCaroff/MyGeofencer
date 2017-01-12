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
    private MyGeofencesAdapterDelegate delegate;

    public MyGeofencesAdapter(List<MyGeofence> dataset, MyGeofencesAdapterDelegate delegate) {
        this.dataset = dataset;
        this.delegate = delegate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mygeofence_recylcyerview_item, parent, false);

        ViewHolder vh = new ViewHolder(v, delegate);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyGeofence geofence = dataset.get(position);

        holder.getName().setText(geofence.getName().toString());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View rootView;
        private TextView name;

        public ViewHolder(View itemView, final MyGeofencesAdapterDelegate delegate) {
            super(itemView);
            rootView = itemView;

            name = (TextView) itemView.findViewById(R.id.name);

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.itemClicked(getAdapterPosition());
                }
            });
        }

        public TextView getName() {
            return name;
        }
    }

    public List<MyGeofence> getDataset() {
        return dataset;
    }

    public interface MyGeofencesAdapterDelegate {
        void itemClicked(int position);
    }
}

package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripViewHolder> {

    private Context context;
    private ArrayList<Trip> tripList;
    private ArrayList<Trip> originalList;

    private ActivityResultLauncher<Intent> editDeleteLauncher;

    public TripsAdapter(Context context, ArrayList<Trip> tripList,
                        ActivityResultLauncher<Intent> editDeleteLauncher) {

        this.context = context;
        this.tripList = tripList;
        this.originalList = new ArrayList<>(tripList);
        this.editDeleteLauncher = editDeleteLauncher;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {

        Trip t = tripList.get(position);

        holder.txtCountry.setText("From: " + t.getOrigin());
        holder.txtDestination.setText("To: " + t.getDestination());
        holder.txtDate.setText("Date: " + t.getDate());
        holder.txtBudget.setText("Budget: " + t.getBudget());
        holder.txtType.setText("Type: " + t.getType());
        holder.txtNotes.setText("Notes: " + t.getNotes());

        if (t.getImageUri() != null && !t.getImageUri().isEmpty()) {

            holder.imgTrip.setImageURI(android.net.Uri.parse(t.getImageUri()));

        } else if (t.getImageRes() != 0) {

            holder.imgTrip.setImageResource(t.getImageRes());

        } else {

            holder.imgTrip.setImageResource(R.drawable.travel2);
        }


        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, TripDetailsActivity.class);
            i.putExtra("trip", t);
            i.putExtra("index", position);
            editDeleteLauncher.launch(i);
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTrip;
        TextView txtCountry, txtDestination, txtDate, txtBudget, txtType, txtNotes;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTrip = itemView.findViewById(R.id.imgTrip);
            txtCountry = itemView.findViewById(R.id.txtCountry);
            txtDestination = itemView.findViewById(R.id.txtDestination);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtBudget = itemView.findViewById(R.id.txtBudget);
            txtType = itemView.findViewById(R.id.txtType);
            txtNotes = itemView.findViewById(R.id.txtNotes);
        }
    }

     public void searchByDestination(String text) {
        text = text.toLowerCase().trim();

        tripList.clear();

        if (text.isEmpty()) {
            tripList.addAll(originalList);
        } else {
            for (Trip t : originalList) {
                if (t.getDestination().toLowerCase().contains(text)) {
                    tripList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }

      public void updateList(ArrayList<Trip> newList) {
        this.tripList = new ArrayList<>(newList);
        this.originalList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }
}

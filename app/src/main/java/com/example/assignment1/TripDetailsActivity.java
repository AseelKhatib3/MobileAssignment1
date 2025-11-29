package com.example.assignment1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TripDetailsActivity extends AppCompatActivity {

    private Trip trip;
    private int index;

    private ImageView imgTrip;
    private TextView txtOrigin, txtDestination, txtDate, txtBudget, txtNotes, txtType;
    private Button btnEdit, btnDelete;

    ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                            Trip updatedTrip = (Trip) result.getData().getSerializableExtra("updatedTrip");
                            if (updatedTrip == null) return;

                            Intent back = new Intent();
                            back.putExtra("updatedTrip", updatedTrip);
                            back.putExtra("index", index);

                            setResult(RESULT_OK, back);
                            finish();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        trip = (Trip) getIntent().getSerializableExtra("trip");
        index = getIntent().getIntExtra("index", -1);

        if (trip == null || index < 0) {
            finish();
            return;
        }

        initViews();
        fillData();
        setupListeners();
    }

    private void initViews() {
        imgTrip = findViewById(R.id.imgTripDetails);
        txtOrigin = findViewById(R.id.txtOriginDetails);
        txtDestination = findViewById(R.id.txtDestinationDetails);
        txtDate = findViewById(R.id.txtDateDetails);
        txtBudget = findViewById(R.id.txtBudgetDetails);
        txtNotes = findViewById(R.id.txtNotesDetails);
        txtType = findViewById(R.id.txtTypeDetails);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void fillData() {

        txtOrigin.setText("From: " + trip.getOrigin());
        txtDestination.setText("To: " + trip.getDestination());
        txtDate.setText("Date: " + trip.getDate());
        txtBudget.setText("Budget: " + trip.getBudget());
        txtNotes.setText("Notes: " + trip.getNotes());
        txtType.setText("Type: " + trip.getType());

        int img = trip.getImageRes() != 0 ? trip.getImageRes() : R.drawable.land;

        imgTrip.setImageResource(img);
    }

    private void setupListeners() {

        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(TripDetailsActivity.this, AddTripActivity.class);
            i.putExtra("editTrip", trip);
            editLauncher.launch(i);
        });

        btnDelete.setOnClickListener(v -> {
            Intent i = new Intent();
            i.putExtra("deleteIndex", index);
            setResult(RESULT_OK, i);
            finish();
        });
    }
}

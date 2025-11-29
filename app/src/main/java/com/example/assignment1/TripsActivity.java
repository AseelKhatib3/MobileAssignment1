package com.example.assignment1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    RecyclerView recyclerTrips;
    ArrayList<Trip> tripList = new ArrayList<>();
    TripsAdapter adapter;
    ImageView btnAddTrip;
    EditText inputSearch;

    private static final String KEY_RECYCLER_STATE = "recycler_state";
    private static final String KEY_SEARCH_QUERY = "search_query";
    private Parcelable recyclerViewState;

    ActivityResultLauncher<Intent> addTripLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Trip newTrip = (Trip) result.getData().getSerializableExtra("newTrip");

                    if (newTrip != null) {
                        tripList.add(0, newTrip);
                        adapter.updateList(tripList);
                        recyclerTrips.scrollToPosition(0);
                        saveTrips();
                    }
                }
            });

    ActivityResultLauncher<Intent> editDeleteLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Intent data = result.getData();

                    // Update Trip
                    if (data.hasExtra("updatedTrip")) {

                        Trip updated = (Trip) data.getSerializableExtra("updatedTrip");
                        int index = data.getIntExtra("index", -1);

                        if (index != -1 && index < tripList.size()) {
                            tripList.set(index, updated);
                            adapter.updateList(tripList);
                            saveTrips();
                        }
                    }

                    if (data.hasExtra("deleteIndex")) {
                        int index = data.getIntExtra("deleteIndex", -1);

                        if (index != -1 && index < tripList.size()) {

                            new AlertDialog.Builder(TripsActivity.this)
                                    .setTitle("Delete Trip")
                                    .setMessage("Are you sure you want to delete this trip?")
                                    .setPositiveButton("Yes", (dialog, which) -> {

                                        tripList.remove(index);
                                        adapter.updateList(tripList);
                                        saveTrips();

                                    })
                                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                                    .show();
                        }
                    }
                }
            });

    private void saveTrips() {
        ArrayList<Trip> userTrips = new ArrayList<>();

        for (Trip t : tripList) {
            if (t.getImageRes() == 0) {
                userTrips.add(t);
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(userTrips);

        getSharedPreferences("Trips", MODE_PRIVATE)
                .edit()
                .putString("userTrips", json)
                .apply();
    }

    private void loadAllTrips() {

        tripList.clear();

        loadDefaultTrips();

        String json = getSharedPreferences("Trips", MODE_PRIVATE)
                .getString("userTrips", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Trip>>() {}.getType();
            ArrayList<Trip> saved = gson.fromJson(json, type);

            if (saved != null) {
                tripList.addAll(saved);
            }
        }
    }

    private void loadDefaultTrips() {
        tripList.add(new Trip("Egypt", "Italy", "11/4/2025", "450$", "Family", "See the Colosseum", R.drawable.rome));
        tripList.add(new Trip("Oman", "Dubai", "20/7/2025", "500$", "Business", "Meet client", R.drawable.dubai));
        tripList.add(new Trip("Palestine", "Japan", "2/3/2025", "900$", "Vacation", "Cherry blossom season", R.drawable.tokyo));
        tripList.add(new Trip("Saudi Arabia", "Egypt", "25/8/2025", "250$", "Vacation", "Explore the Pyramids", R.drawable.egypt));
        tripList.add(new Trip("Lebanon", "Turkey", "18/9/2025", "350$", "Vacation", "Visit Hagia Sophia", R.drawable.turkey));
        tripList.add(new Trip("Brazil", "Brazil", "10/10/2025", "700$", "Adventure", "Christ the Redeemer visit", R.drawable.brazil));
        tripList.add(new Trip("Germany", "Belgium", "5/11/2025", "600$", "Cultural", "Explore Grand Place", R.drawable.belgium));
        tripList.add(new Trip("Hong Kong", "China", "15/12/2025", "800$", "Historical", "Great Wall tour", R.drawable.china));
        tripList.add(new Trip("Thailand", "Malaysia", "3/1/2026", "550$", "Vacation", "Petronas Towers trip", R.drawable.malaysia));
        tripList.add(new Trip("USA", "Mexico", "22/2/2026", "620$", "Cultural", "Visit Teotihuacan", R.drawable.mexico));
        tripList.add(new Trip("Finland", "Norway", "18/3/2026", "950$", "Nature", "Northern lights tour", R.drawable.norway));
        tripList.add(new Trip("Morocco", "Spain", "29/4/2026", "500$", "Vacation", "Sagrada Familia trip", R.drawable.spain));
        tripList.add(new Trip("Jordan", "Paris", "12/5/2025", "300$", "Vacation", "Visit Eiffel Tower", R.drawable.paris));
        tripList.add(new Trip("Austria", "Switzerland", "8/5/2026", "1200$", "Luxury", "Swiss Alps journey", R.drawable.switzerland));
        tripList.add(new Trip("Jordan", "Palestine", "14/6/2026", "400$", "Cultural", "Visit Jerusalem & historical sites", R.drawable.palestine));
        tripList.add(new Trip("Ethiopia", "Sudan", "21/7/2026", "350$", "Exploration", "Nile River & Pyramids of Meroë", R.drawable.sudan));
        tripList.add(new Trip("Algeria", "Tunisia", "9/8/2026", "300$", "Vacation", "Sidi Bou Said & beaches", R.drawable.tunisia));
        tripList.add(new Trip("Denmark", "Sweden", "30/9/2026", "1100$", "Nature", "Stockholm & northern landscapes", R.drawable.sweden));
        tripList.add(new Trip("India", "Maldives", "12/10/2026", "1500$", "Luxury", "Resort stay & snorkeling", R.drawable.maldives));
        tripList.add(new Trip("Qatar", "South Korea", "7/11/2026", "850$", "Cultural", "Visit Gyeongbokgung Palace", R.drawable.southkorea));
        tripList.add(new Trip("Bahrain", "Singapore", "19/2/2026", "1100$", "Luxury", "Marina Bay Sands experience", R.drawable.singapore));
        tripList.add(new Trip("Kuwait", "Netherlands", "4/6/2026", "900$", "Vacation", "Explore Amsterdam canals", R.drawable.netherlands));
        tripList.add(new Trip("Iraq", "Greece", "23/7/2026", "650$", "Vacation", "Visit Santorini island", R.drawable.greece));
        tripList.add(new Trip("Syria", "Germany", "12/1/2026", "780$", "Business", "Attend tech conference in Berlin", R.drawable.germany));
        tripList.add(new Trip("Canada", "UK", "15/9/2026", "1300$", "Cultural", "Big Ben & London tour", R.drawable.uk));
        tripList.add(new Trip("France", "Portugal", "8/4/2026", "520$", "Adventure", "Explore Lisbon & coastal cliffs", R.drawable.portugal));
        tripList.add(new Trip("Australia", "New Zealand", "2/2/2026", "1400$", "Nature", "Hobbiton movie set tour", R.drawable.newzealand));
        tripList.add(new Trip("South Africa", "Kenya", "9/12/2026", "600$", "Adventure", "Safari wildlife experience", R.drawable.kenya));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        recyclerTrips = findViewById(R.id.recyclerTrips);
        btnAddTrip = findViewById(R.id.btnAddTrip);
        inputSearch = findViewById(R.id.inputSearch);
        Button btnLogout = findViewById(R.id.btnLogout);

        recyclerTrips.setLayoutManager(new LinearLayoutManager(this));

        loadAllTrips();

        adapter = new TripsAdapter(this, tripList, editDeleteLauncher);
        recyclerTrips.setAdapter(adapter);

        btnAddTrip.setOnClickListener(v -> {
            Intent i = new Intent(TripsActivity.this, AddTripActivity.class);
            addTripLauncher.launch(i);
        });

        inputSearch.setHint("Search by destination...");
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.searchByDestination(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int s2, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnLogout.setOnClickListener(v -> {

            SharedPreferences prefs = getSharedPreferences("LoginPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent i = new Intent(TripsActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_SEARCH_QUERY, inputSearch.getText().toString());

        if (recyclerTrips.getLayoutManager() != null) {
            recyclerViewState = recyclerTrips.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(KEY_RECYCLER_STATE, recyclerViewState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        inputSearch.setText(savedInstanceState.getString(KEY_SEARCH_QUERY, ""));
        recyclerViewState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recyclerViewState != null && recyclerTrips.getLayoutManager() != null) {
            recyclerTrips.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            recyclerViewState = null;
        }
    }
}

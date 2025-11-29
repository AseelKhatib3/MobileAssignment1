package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity {

    EditText edtOrigin, edtDestination, edtDate, edtBudget, edtNotes;
    RadioGroup radioGroupType;
    RadioButton rbVacation, rbBusiness, rbFamily;
    Button btnSaveTrip;

    Trip editTrip = null;
    boolean isEditingBudget = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initViews();
        setupListeners();

        editTrip = (Trip) getIntent().getSerializableExtra("editTrip");

        if (editTrip != null) {
            fillFieldsForEdit();
            btnSaveTrip.setText("Save Changes");
        }

        setupBudgetPrefix();
    }

    private void initViews() {

        edtOrigin = findViewById(R.id.edtOrigin);
        edtDestination = findViewById(R.id.edtDestination);
        edtDate = findViewById(R.id.edtDate);
        edtBudget = findViewById(R.id.edtBudget);
        edtNotes = findViewById(R.id.edtNotes);

        radioGroupType = findViewById(R.id.radioGroupType);
        rbVacation = findViewById(R.id.rbVacation);
        rbBusiness = findViewById(R.id.rbBusiness);
        rbFamily = findViewById(R.id.rbFamily);

        btnSaveTrip = findViewById(R.id.btnSaveTrip);
    }

    private void setupListeners() {
        edtDate.setOnClickListener(v -> openDatePicker());
        btnSaveTrip.setOnClickListener(v -> saveTrip());
    }

    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) ->
                        edtDate.setText(day + "/" + (month + 1) + "/" + year),
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void fillFieldsForEdit() {

        edtOrigin.setText(editTrip.getOrigin());
        edtDestination.setText(editTrip.getDestination());
        edtDate.setText(editTrip.getDate());

        String budget = editTrip.getBudget();
        if (!budget.startsWith("$")) {
            budget = "$" + budget;
        }
        edtBudget.setText(budget);

        edtNotes.setText(editTrip.getNotes());

        switch (editTrip.getType()) {
            case "Vacation": rbVacation.setChecked(true); break;
            case "Business": rbBusiness.setChecked(true); break;
            case "Family": rbFamily.setChecked(true); break;
        }
    }

    private void setupBudgetPrefix() {

        edtBudget.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isEditingBudget) return;

                isEditingBudget = true;

                String value = s.toString().replace("$", "");

                if (!value.isEmpty()) {
                    edtBudget.setText("$" + value);
                    edtBudget.setSelection(edtBudget.getText().length());
                }

                isEditingBudget = false;
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void saveTrip() {

        if (edtOrigin.getText().toString().trim().isEmpty()
                || edtDestination.getText().toString().trim().isEmpty()
                || edtDate.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int typeId = radioGroupType.getCheckedRadioButtonId();
        String type = "";

        if (typeId == rbVacation.getId()) type = "Vacation";
        else if (typeId == rbBusiness.getId()) type = "Business";
        else if (typeId == rbFamily.getId()) type = "Family";

        if (type.isEmpty()) {
            Toast.makeText(this, "Please select trip type", Toast.LENGTH_SHORT).show();
            return;
        }

        String budget = edtBudget.getText().toString().replace("$", "").trim();

        if (editTrip != null) {

            editTrip.setOrigin(edtOrigin.getText().toString());
            editTrip.setDestination(edtDestination.getText().toString());
            editTrip.setDate(edtDate.getText().toString());
            editTrip.setBudget("$" + budget);
            editTrip.setNotes(edtNotes.getText().toString());
            editTrip.setType(type);

            Intent result = new Intent();
            result.putExtra("updatedTrip", editTrip);
            setResult(RESULT_OK, result);
            finish();
            return;
        }

        int defaultImage = R.drawable.land;

        Trip newTrip = new Trip(
                edtOrigin.getText().toString(),
                edtDestination.getText().toString(),
                edtDate.getText().toString(),
                "$" + budget,
                type,
                edtNotes.getText().toString(),
                defaultImage
        );

        Intent result = new Intent();
        result.putExtra("newTrip", newTrip);
        setResult(RESULT_OK, result);
        finish();
    }
}

package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText edtSUUsername, edtSUEmail, edtSUPassword, edtSUConfirm;
    Button btnCreateAccount;

    SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "LoginPref";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtSUUsername = findViewById(R.id.edtSUUsername);
        edtSUEmail = findViewById(R.id.edtSUEmail);
        edtSUPassword = findViewById(R.id.edtSUPassword);
        edtSUConfirm = findViewById(R.id.edtSUConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        TextView txtHaveAccount = findViewById(R.id.txtHaveAccount);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btnCreateAccount.setOnClickListener(v -> createAccount());

        txtHaveAccount.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void createAccount() {

        String user = edtSUUsername.getText().toString().trim();
        String email = edtSUEmail.getText().toString().trim();
        String pass = edtSUPassword.getText().toString().trim();
        String confirm = edtSUConfirm.getText().toString().trim();

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 4) {
            Toast.makeText(this, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        String existingUser = sharedPreferences.getString(KEY_USER, "");
        if (user.equals(existingUser)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, user);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASS, pass);
        editor.apply();

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }
}

package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtSignUp;
    CheckBox chkRemember;

    SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "LoginPref";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);
        chkRemember = findViewById(R.id.chkRemember);

        // Shared Preferences for login
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        boolean remember = sharedPreferences.getBoolean(KEY_REMEMBER, false);
        String savedUser = sharedPreferences.getString(KEY_USER, "");
        String savedPass = sharedPreferences.getString(KEY_PASS, "");

        if (remember && !savedUser.isEmpty() && !savedPass.isEmpty()) {
            startActivity(new Intent(LoginActivity.this, TripsActivity.class));
            finish();
            return;
        }

        txtSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Login Button
        btnLogin.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            String storedUser = sharedPreferences.getString(KEY_USER, "");
            String storedPass2 = sharedPreferences.getString(KEY_PASS, "");

            if (user.equals(storedUser) && pass.equals(storedPass2)) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_REMEMBER, chkRemember.isChecked());
                editor.apply();

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, TripsActivity.class));
                finish();

            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials. Please sign up first.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

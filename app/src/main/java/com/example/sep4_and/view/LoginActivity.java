package com.example.sep4_and.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.AuthRequest;
import com.example.sep4_and.model.AuthResponse;
import com.example.sep4_and.model.User;
import com.example.sep4_and.utils.Auth0Config;
import com.example.sep4_and.utils.JWTUtils;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private static final String PREFS_NAME = "auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        Button signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(v -> handleLogin(emailEditText, passwordEditText));
        signupButton.setOnClickListener(v -> navigateToSignup());

        userViewModel.getToken().observe(this, this::saveTokenAndRedirect);
        userViewModel.getAuthError().observe(this, error -> Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show());
    }

    private void handleLogin(EditText emailEditText, EditText passwordEditText) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        userViewModel.login(email, password);
    }

    private void saveTokenAndRedirect(String token) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", token);
        editor.apply();
        redirectToMainActivity();
    }

    private void navigateToSignup() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("navigateTo", "SignupFragment");
        startActivity(intent);
        finish();
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
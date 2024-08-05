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
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.utils.TokenManager;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private static final String PREFS_NAME = "auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TokenManager.init(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(v -> handleLogin());
        signUpButton.setOnClickListener(v -> navigateToSignup());

//        userViewModel.getToken().observe(this, this::saveTokenAndRedirect);
//        userViewModel.getAuthError().observe(this, error -> Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show());
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        userViewModel.login(email, password).observe(this, user -> {
            if (user != null) {
                String userIdString = String.valueOf(user.getId());

                TokenManager.saveToken(userIdString);
                RetrofitInstance.setAuthToken(userIdString);

                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                redirectToMainActivity();
            } else {
                Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
            }
        });
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
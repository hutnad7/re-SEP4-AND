package com.example.sep4_and.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.utils.TokenManager;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private ImageView togglePasswordVisibility;
    private boolean isPasswordVisible = false;
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
        registerButton = findViewById(R.id.signup_button);
        togglePasswordVisibility = findViewById(R.id.toggle_password_visibility);

        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> navigateToSignup());

        togglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.ic_visibility_off);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.ic_visibility);
                }
                // Move the cursor to the end of the text
                passwordEditText.setSelection(passwordEditText.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });
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
                TokenManager.saveToken(String.valueOf(user.getId()));
                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                userViewModel.getCurrentUser().observe(this, currentUser -> {
                    if (currentUser != null) {
                        Log.d("LoginActivity", "Current user set: " + currentUser.getId());
                        redirectToMainActivity();
                    } else {
                        Log.d("LoginActivity", "Current user is null");
                        Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }
                });
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
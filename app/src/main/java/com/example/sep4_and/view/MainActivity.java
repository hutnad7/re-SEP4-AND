package com.example.sep4_and.view;

import static com.example.sep4_and.utils.SharedPreferencesManager.PREFS_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sep4_and.R;
import com.example.sep4_and.utils.SharedPreferencesManager;
import com.example.sep4_and.utils.TokenManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class MainActivity extends AppCompatActivity {
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TokenManager.init(this);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        if (sharedPreferencesManager.isFirstLaunch()) {
            sharedPreferencesManager.clearToken();
        }

        Intent intent = getIntent();
        String navigateTo = intent.getStringExtra("navigateTo");

        if ("SignupFragment".equals(navigateTo)) {
            loadFragment(new SignupFragment());
            return;
        }

        if (TokenManager.getToken() == null) {
            redirectToLogin();
            return;
        }

        if (savedInstanceState == null) {
            loadFragment(new DashBoardFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void redirectToLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
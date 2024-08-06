package com.example.sep4_and.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sep4_and.R;
import com.example.sep4_and.utils.SharedPreferencesManager;
import com.example.sep4_and.utils.TokenManager;


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
            loadFragment(new CentralFragment());
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
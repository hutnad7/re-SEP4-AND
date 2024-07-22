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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/*public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button to go to User Fragment
        Button buttonGoToUserFragment = findViewById(R.id.buttonGoToUserFragment);
        buttonGoToUserFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new UserFragment());
            }
        });

        // Button to go to User List Fragment
        Button buttonGoToUserListFragment = findViewById(R.id.buttonGoToUserListFragment);
        buttonGoToUserListFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new UserListFragment());
            }
        });

        // Button to go to Add GreenHouse Fragment
        Button buttonGoToAddGreenHouse = findViewById(R.id.buttonGoToAddGreenHouse);
        buttonGoToAddGreenHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new AddGreenHouseFragment());
            }
        });

        // Button to go to View GreenHouses Fragment
        Button buttonGoToViewGreenHouses = findViewById(R.id.buttonGoToViewGreenHouses);
        buttonGoToViewGreenHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new ViewGreenHousesFragment());
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new MeasurementsFragment());
            }
        });

        // AddThreshold needs to be accessed from a greenhouse to work properly
//        Button buttonGoToAddThreshold = findViewById(R.id.buttonGoToAddThreshold);
//        buttonGoToAddThreshold.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToFragment(new AddThresholdFragment());
//            }
//        });
        Button buttonGoToAddNotification = findViewById(R.id.buttonGoToAddNotification);
        buttonGoToAddNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new AddNotificationFragment());
            }
        });

        Button buttonGoToViewNotifications = findViewById(R.id.buttonGoToViewNotifications);
        buttonGoToViewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new ViewNotificationsFragment());
            }
        });
    }


    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Replace current fragment
        transaction.addToBackStack(null); // Add to back stack to allow user to navigate back
        transaction.commit();
    }
}*/

public class MainActivity extends AppCompatActivity {
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if (!sharedPreferencesManager.isUserLoggedIn()) {
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
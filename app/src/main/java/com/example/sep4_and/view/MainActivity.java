package com.example.sep4_and.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sep4_and.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
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

        Button buttonGoToAddThreshold = findViewById(R.id.buttonGoToAddThreshold);
        buttonGoToAddThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new AddThresholdFragment());
            }
        });
    }


    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Replace current fragment
        transaction.addToBackStack(null); // Add to back stack to allow user to navigate back
        transaction.commit();
    }
}
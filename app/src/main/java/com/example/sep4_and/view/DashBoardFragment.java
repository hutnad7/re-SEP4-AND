package com.example.sep4_and.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sep4_and.R;
import com.example.sep4_and.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;



public class DashBoardFragment extends Fragment {

    private static final String PREFS_NAME = "auth";
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        // Initialize UserViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe LiveData
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            // Handle user data
            if (user != null) {
                // User is logged in, perform actions
            }
        });

        userViewModel.getAuthError().observe(getViewLifecycleOwner(), error -> {
            // Handle authentication error
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        userViewModel.getToken().observe(getViewLifecycleOwner(), token -> {
            // Handle token updates
            if (token != null) {
                // Token received, perform actions
            }
        });

        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> logout());

        // Button to go to User Fragment
        Button buttonGoToUserFragment = view.findViewById(R.id.buttonGoToUserFragment);
        buttonGoToUserFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new UserFragment());
            }
        });

        // Button to go to User List Fragment
        Button buttonGoToUserListFragment = view.findViewById(R.id.buttonGoToUserListFragment);
        buttonGoToUserListFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new UserListFragment());
            }
        });

        // Button to go to Add GreenHouse Fragment
        Button buttonGoToAddGreenHouse = view.findViewById(R.id.buttonGoToAddGreenHouse);
        buttonGoToAddGreenHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new AddGreenHouseFragment());
            }
        });

        // Button to go to View GreenHouses Fragment
        Button buttonGoToViewGreenHouses = view.findViewById(R.id.buttonGoToViewGreenHouses);
        buttonGoToViewGreenHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new ViewGreenHousesFragment());
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new MeasurementsFragment());
            }
        });

        // Button to go to Add Notification Fragment
        Button buttonGoToAddNotification = view.findViewById(R.id.buttonGoToAddNotification);
        buttonGoToAddNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new AddNotificationFragment());
            }
        });

        // Button to go to View Notifications Fragment
        Button buttonGoToViewNotifications = view.findViewById(R.id.buttonGoToViewNotifications);
        buttonGoToViewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new ViewNotificationsFragment());
            }
        });



        // Button to go to Historical Measurements Fragment
        Button buttonGoToHistoricalMeasurements = view.findViewById(R.id.buttonGoToHistoricalMeasurements);
        buttonGoToHistoricalMeasurements.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.historicalMeasurementsFragment);
        });


        return view;
    }

    private void logout() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, requireActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("access_token");
        editor.apply();

        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        requireActivity().finish();
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Replace current fragment
        transaction.addToBackStack(null); // Add to back stack to allow user to navigate back
        transaction.commit();
    }
}
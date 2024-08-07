package com.example.sep4_and.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.list.GreenHouseAdapter;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.utils.DialogHelper;
import com.example.sep4_and.utils.TokenManager;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.MeasurementViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;


public class DashBoardFragment extends Fragment {

    private GreenHouseViewModel greenHouseViewModel;
    private MeasurementViewModel measurementViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private GreenHouseAdapter adapter;
    private TextView greenhouseCountTextView;
    private TextView helloTextView;
    private FloatingActionButton floatingActionAddGreenhouse;
    private FrameLayout logoutButton;
    private FrameLayout notificationIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        recyclerView = view.findViewById(R.id.greenhouseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        greenhouseCountTextView = view.findViewById(R.id.greenhouseCount);
        helloTextView = view.findViewById(R.id.helloText);
        floatingActionAddGreenhouse = view.findViewById(R.id.floatingActionAddGreenhouse);
        logoutButton = view.findViewById(R.id.logoutButton);
        notificationIcon = view.findViewById(R.id.notificationIcon);

        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        measurementViewModel = new ViewModelProvider(this).get(MeasurementViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        adapter = new GreenHouseAdapter(
                greenHouseViewModel,
                this::onViewMeasurementsButtonClick,
                this::onDeleteButtonClick,
                this::onViewGreenhouseDetailsButtonClick
        );

        recyclerView.setAdapter(adapter);

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), currentUser -> {
            if (currentUser != null) {
                greenHouseViewModel.getGreenHousesByUserId(currentUser.getId()).observe(getViewLifecycleOwner(), greenHouses -> {
                    adapter.setGreenHouses(greenHouses);
                    updateGreenhouseCount(greenHouses.size());
                });
                updateHelloText(currentUser.getFirstName());
            }
        });

        floatingActionAddGreenhouse.setOnClickListener(v -> navigateToFragment(new AddGreenHouseFragment()));
        logoutButton.setOnClickListener(v -> showLogoutDialog());
        notificationIcon.setOnClickListener(v -> navigateToFragment(new ViewNotificationsFragment()));

        return view;
    }




    //TODO: Make unified method for replacing text
    private void updateGreenhouseCount(int count) {
        String text = "You have " + count + " Greenhouses";
        greenhouseCountTextView.setText(text);
    }

    private void updateHelloText(String firstName) {
        String text = "Hello, " + firstName;
        helloTextView.setText(text);
    }

    private void onViewGreenhouseDetailsButtonClick(GreenHouse greenHouse) {
        GreenhouseDetailsFragment detailsFragment = GreenhouseDetailsFragment.newInstance(greenHouse.getId());
        navigateToFragment(detailsFragment);
    }

    private void onViewMeasurementsButtonClick(GreenHouse greenHouse) {
        measurementViewModel.getMeasurementsForGreenHouse(greenHouse.getId()).observe(getViewLifecycleOwner(), measurements -> {
            if (measurements == null || measurements.isEmpty()) {
                //TODO: Remove dummy Measurement
                Measurement fictitiousMeasurement = new Measurement(MeasurementType.TEMPERATURE, 0.0f, new Date(), greenHouse.getId());
                measurementViewModel.insert(fictitiousMeasurement);
            } else {
                ViewMeasurementsFragment viewMeasurementsFragment = ViewMeasurementsFragment.newInstance(greenHouse.getId());
                navigateToFragment(viewMeasurementsFragment);
            }
        });
    }

    private void onDeleteButtonClick(GreenHouse greenHouse) {
        greenHouseViewModel.delete(greenHouse);
    }

    //Setup dialog box
    private void showLogoutDialog() {
        //Setup dialog text
        DialogHelper dialogHelper = new DialogHelper(
                getContext(),
                "Confirm Logout",
                "Are you sure you want to logout?",
                "Cancel",
                "Confirm"
        );
        //Dialog
        dialogHelper.showDialog(this::logout);
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Add to back stack to allow user to navigate back
        transaction.commit();
    }
    private void logout() {
        TokenManager.clearToken();
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        requireActivity().finish();
    }
}
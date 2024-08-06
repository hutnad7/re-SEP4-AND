package com.example.sep4_and.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.list.GreenHouseAdapter;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.MeasurementViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.util.Date;
import java.util.List;



public class ViewGreenHousesFragment extends Fragment {

    private GreenHouseViewModel greenHouseViewModel;
    private MeasurementViewModel measurementViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private GreenHouseAdapter adapter;
    private TextView greenhouseCountTextView;
    private TextView helloTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_greenhouses, container, false);

        recyclerView = view.findViewById(R.id.greenhouseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        greenhouseCountTextView = view.findViewById(R.id.greenhouseCount);
        helloTextView = view.findViewById(R.id.helloText);

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

        return view;
    }

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
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void onViewMeasurementsButtonClick(GreenHouse greenHouse) {
        measurementViewModel.getMeasurementsForGreenHouse(greenHouse.getId()).observe(getViewLifecycleOwner(), measurements -> {
            if (measurements == null || measurements.isEmpty()) {
                Measurement fictitiousMeasurement = new Measurement(MeasurementType.TEMPERATURE, 0.0f, new Date(), greenHouse.getId());
                measurementViewModel.insert(fictitiousMeasurement);
            } else {
                ViewMeasurementsFragment viewMeasurementsFragment = ViewMeasurementsFragment.newInstance(greenHouse.getId());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, viewMeasurementsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void onDeleteButtonClick(GreenHouse greenHouse) {
        greenHouseViewModel.delete(greenHouse);
    }
}
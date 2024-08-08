package com.example.sep4_and.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.list.GreenHouseSpinnerAdapter;
import com.example.sep4_and.list.HistoricalDataAdapter;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.MeasurementViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoricalDataFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouse_id";
    private int initialGreenHouseId;
    private Spinner spinnerGreenhouse;
    private Spinner spinnerMeasurementType;
    private RecyclerView recyclerViewHistoricalData;
    private Button buttonSelectDateRange;
    private Button buttonClearDateRange;
    private MeasurementViewModel measurementViewModel;
    private GreenHouseViewModel greenHouseViewModel;
    private UserViewModel userViewModel;
    private List<GreenHouse> greenHouses;
    private List<MeasurementType> measurementTypes;
    private HistoricalDataAdapter adapter;
    private Long startDate;
    private Long endDate;

    public static HistoricalDataFragment newInstance(int greenHouseId) {
        HistoricalDataFragment fragment = new HistoricalDataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GREENHOUSE_ID, greenHouseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            initialGreenHouseId = getArguments().getInt(ARG_GREENHOUSE_ID);
        }
        measurementViewModel = new ViewModelProvider(this).get(MeasurementViewModel.class);
        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        measurementTypes = Arrays.asList(MeasurementType.values());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historical_data, container, false);

        spinnerGreenhouse = view.findViewById(R.id.spinnerGreenhouse);
        spinnerMeasurementType = view.findViewById(R.id.spinnerMeasurementType);
        recyclerViewHistoricalData = view.findViewById(R.id.recyclerViewHistoricalData);
        buttonSelectDateRange = view.findViewById(R.id.buttonSelectDateRange);
        buttonClearDateRange = view.findViewById(R.id.buttonClearDateRange);
        recyclerViewHistoricalData.setLayoutManager(new LinearLayoutManager(getContext()));

        setupSpinners();

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), currentUser -> {
            if (currentUser != null) {
                loadGreenHouses(currentUser.getId());
            }
        });

        buttonSelectDateRange.setOnClickListener(v -> showDateRangePicker());
        buttonClearDateRange.setOnClickListener(v -> clearDateRange());

        return view;
    }

    private void setupSpinners() {
        ArrayAdapter<MeasurementType> measurementTypeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, measurementTypes);
        measurementTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerMeasurementType.setAdapter(measurementTypeAdapter);

        spinnerMeasurementType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadHistoricalData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spinnerGreenhouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadHistoricalData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void loadGreenHouses(int userId) {
        greenHouseViewModel.getGreenHousesByUserId(userId).observe(getViewLifecycleOwner(), greenHouses -> {
            this.greenHouses = greenHouses;
            GreenHouseSpinnerAdapter greenhouseAdapter = new GreenHouseSpinnerAdapter(getContext(), greenHouses);
            spinnerGreenhouse.setAdapter(greenhouseAdapter);

            // Set the initial selected greenhouse
            for (int i = 0; i < greenHouses.size(); i++) {
                if (greenHouses.get(i).getId() == initialGreenHouseId) {
                    spinnerGreenhouse.setSelection(i);
                    break;
                }
            }
        });
    }

    private void loadHistoricalData() {
        GreenHouse selectedGreenHouse = (GreenHouse) spinnerGreenhouse.getSelectedItem();
        MeasurementType selectedMeasurementType = (MeasurementType) spinnerMeasurementType.getSelectedItem();

        if (selectedGreenHouse != null && selectedMeasurementType != null) {
            LiveData<List<Measurement>> measurementsLiveData;
            if (startDate != null && endDate != null) {
                measurementsLiveData = measurementViewModel.getMeasurementsForGreenHouseWithinDateRange(selectedGreenHouse.getId(), startDate, endDate);
            } else {
                measurementsLiveData = measurementViewModel.getMeasurementsForGreenHouse(selectedGreenHouse.getId());
            }

            measurementsLiveData.observe(getViewLifecycleOwner(), measurements -> {
                List<Measurement> filteredMeasurements = filterMeasurementsByType(measurements, selectedMeasurementType);
                adapter = new HistoricalDataAdapter(filteredMeasurements);
                recyclerViewHistoricalData.setAdapter(adapter);
            });
        }
    }

    private List<Measurement> filterMeasurementsByType(List<Measurement> measurements, MeasurementType type) {
        List<Measurement> filteredMeasurements = new ArrayList<>();
        for (Measurement measurement : measurements) {
            if (measurement.getType() == type) {
                filteredMeasurements.add(measurement);
            }
        }
        return filteredMeasurements;
    }

    private void showDateRangePicker() {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Date Range")
                .setCalendarConstraints(constraintsBuilder.build());

        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.show(getParentFragmentManager(), datePicker.toString());

        datePicker.addOnPositiveButtonClickListener(selection -> {
            if (selection != null) {
                startDate = selection.first;
                endDate = selection.second;
                loadHistoricalData();
            }
        });
    }

    private void clearDateRange() {
        startDate = null;
        endDate = null;
        loadHistoricalData();
    }
}
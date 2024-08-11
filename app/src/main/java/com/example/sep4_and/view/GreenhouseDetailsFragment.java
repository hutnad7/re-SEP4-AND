package com.example.sep4_and.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.MeasurementViewModel;
import com.example.sep4_and.viewmodel.ThresholdViewModel;

import java.util.List;

public class GreenhouseDetailsFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouse_id";
    private int greenHouseId;
    private ThresholdViewModel thresholdViewModel;
    private GreenHouseViewModel greenHouseViewModel;
    private MeasurementViewModel measurementViewModel;
    private TextView textViewGreenhouseTitle;
    private TextView textViewGreenhouseLocation;
    private TextView co2Value;
    private TextView humidityValue;
    private TextView tempValue;
    private TextView lightValue;
    private TextView historicalData;

    public static GreenhouseDetailsFragment newInstance(int greenHouseId) {
        GreenhouseDetailsFragment fragment = new GreenhouseDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GREENHOUSE_ID, greenHouseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_greenhouse_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            greenHouseId = getArguments().getInt(ARG_GREENHOUSE_ID);
        }

        textViewGreenhouseTitle = view.findViewById(R.id.textViewGreenhouseTitle);
        textViewGreenhouseLocation = view.findViewById(R.id.textViewGreenhouseLocation);
        co2Value = view.findViewById(R.id.co2Value);
        humidityValue = view.findViewById(R.id.humidityValue);
        tempValue = view.findViewById(R.id.tempValue);
        lightValue = view.findViewById(R.id.lightValue);
        historicalData = view.findViewById(R.id.historicalData);

        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        measurementViewModel = new ViewModelProvider(this).get(MeasurementViewModel.class);

        greenHouseViewModel.getGreenHouseById(greenHouseId).observe(getViewLifecycleOwner(), greenHouse -> {
            if (greenHouse != null) {
                textViewGreenhouseTitle.setText(greenHouse.getName());
                textViewGreenhouseLocation.setText(greenHouse.getLocation());
            }
        });

        thresholdViewModel = new ViewModelProvider(this).get(ThresholdViewModel.class);
        thresholdViewModel.getThresholdsForGreenHouse(greenHouseId).observe(getViewLifecycleOwner(), thresholds -> displayThresholds(view, thresholds));

        loadLatestMeasurements();

        historicalData.setOnClickListener(v -> {
            HistoricalDataFragment historicalDataFragment = HistoricalDataFragment.newInstance(greenHouseId);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, historicalDataFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    //----------------UI stuff, there must be a better way to handle this-------------------------------
    private void loadLatestMeasurements() {
        greenHouseViewModel.getLatestMeasurementForType(greenHouseId, MeasurementType.CO2).observe(getViewLifecycleOwner(), measurement -> {
            if (measurement != null) {
                co2Value.setText(String.format("%s ppm", measurement.getValue()));
            }
        });

        greenHouseViewModel.getLatestMeasurementForType(greenHouseId, MeasurementType.HUMIDITY).observe(getViewLifecycleOwner(), measurement -> {
            if (measurement != null) {
                humidityValue.setText(String.format("%s %%", measurement.getValue()));
            }
        });

        greenHouseViewModel.getLatestMeasurementForType(greenHouseId, MeasurementType.TEMPERATURE).observe(getViewLifecycleOwner(), measurement -> {
            if (measurement != null) {
                tempValue.setText(String.format("%s °C", measurement.getValue()));
            }
        });

        greenHouseViewModel.getLatestMeasurementForType(greenHouseId, MeasurementType.LIGHT).observe(getViewLifecycleOwner(), measurement -> {
            if (measurement != null) {
                lightValue.setText(String.format("%s lux", measurement.getValue()));
            }
        });
    }

    private void displayThresholds(View view, List<Threshold> thresholds) {
        setThreshold(view, R.id.co2MinValue, R.id.co2MaxValue, R.id.co2AddButton, R.id.co2EditButton, MeasurementType.CO2, thresholds);
        setThreshold(view, R.id.humidityMinValue, R.id.humidityMaxValue, R.id.humidityAddButton, R.id.humidityEditButton, MeasurementType.HUMIDITY, thresholds);
        setThreshold(view, R.id.tempMinValue, R.id.tempMaxValue, R.id.tempAddButton, R.id.tempEditButton, MeasurementType.TEMPERATURE, thresholds);
        setThreshold(view, R.id.lightMinValue, R.id.lightMaxValue, R.id.lightAddButton, R.id.lightEditButton, MeasurementType.LIGHT, thresholds);
    }

    //Big ugly and necessary :,)
    private void setThreshold(View view, int minId, int maxId, int addButtonId, int editButtonId, MeasurementType type, List<Threshold> thresholds) {
        TextView minValue = view.findViewById(minId);
        TextView maxValue = view.findViewById(maxId);
        Button addButton = view.findViewById(addButtonId);
        Button editButton = view.findViewById(editButtonId);

        Threshold threshold = findThresholdByType(thresholds, type);

        if (threshold != null) {
            String minFormatted = formatValue(threshold.getMinValue());
            String maxFormatted = formatValue(threshold.getMaxValue());

            String unit = getUnitForType(threshold.getType());
            minValue.setText("Min " + minFormatted + " " + unit);
            maxValue.setText("Max " + maxFormatted + " " + unit);

            minValue.setVisibility(View.VISIBLE);
            maxValue.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(v -> {
                ManageThresholdFragment addThresholdFragment = ManageThresholdFragment.newInstance(greenHouseId, type, threshold.getMinValue(), threshold.getMaxValue());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addThresholdFragment)
                        .addToBackStack(null)
                        .commit();
            });
        } else {
            minValue.setVisibility(View.GONE);
            maxValue.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            addButton.setOnClickListener(v -> {
                ManageThresholdFragment addThresholdFragment = ManageThresholdFragment.newInstance(greenHouseId, type, null, null);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addThresholdFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
    //-------------------------------------------------------------------------------------------------------------

    // Helper methods
    private Threshold findThresholdByType(List<Threshold> thresholds, MeasurementType type) {
        for (Threshold threshold : thresholds) {
            if (threshold.getType() == type) {
                return threshold;
            }
        }
        return null;
    }


    private String formatValue(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
    }

    private String getUnitForType(MeasurementType type) {
        switch (type) {
            case CO2:
                return "ppm";
            case HUMIDITY:
                return "%";
            case TEMPERATURE:
                return "°C";
            case LIGHT:
                return "lux";
            default:
                return "";
        }
    }
}
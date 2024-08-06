package com.example.sep4_and.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.MeasurementViewModel;
import com.example.sep4_and.viewmodel.ThresholdViewModel;

import java.util.List;
import java.text.DecimalFormat;

public class GreenhouseDetailsFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouse_id";
    private int greenHouseId;
    private ThresholdViewModel thresholdViewModel;
    private GreenHouseViewModel greenHouseViewModel;
    private TextView textViewGreenhouseTitle;
    private TextView textViewGreenhouseLocation;

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

        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        greenHouseViewModel.getGreenHouseById(greenHouseId).observe(getViewLifecycleOwner(), new Observer<GreenHouse>() {
            @Override
            public void onChanged(GreenHouse greenHouse) {
                if (greenHouse != null) {
                    textViewGreenhouseTitle.setText(greenHouse.getName());
                    textViewGreenhouseLocation.setText(greenHouse.getLocation());
                }
            }
        });

        thresholdViewModel = new ViewModelProvider(this).get(ThresholdViewModel.class);
        thresholdViewModel.getThresholdsForGreenHouse(greenHouseId).observe(getViewLifecycleOwner(), new Observer<List<Threshold>>() {
            @Override
            public void onChanged(List<Threshold> thresholds) {
                displayThresholds(view, thresholds);
            }
        });

        displayHardcodedMeasurements(view);
    }

   private void displayThresholds(View view, List<Threshold> thresholds) {
       setThreshold(view, R.id.co2MinValue, R.id.co2MaxValue, R.id.co2AddButton, MeasurementType.CO2, thresholds);
       setThreshold(view, R.id.humidityMinValue, R.id.humidityMaxValue, R.id.humidityAddButton, MeasurementType.HUMIDITY, thresholds);
        setThreshold(view, R.id.tempMinValue, R.id.tempMaxValue, R.id.tempAddButton, MeasurementType.TEMPERATURE, thresholds);
       setThreshold(view, R.id.lightMinValue, R.id.lightMaxValue, R.id.lightAddButton, MeasurementType.LIGHT, thresholds);
   }

    private void setThreshold(View view, int minId, int maxId, int buttonId, MeasurementType type, List<Threshold> thresholds) {
        TextView minValue = view.findViewById(minId);
        TextView maxValue = view.findViewById(maxId);
        Button addButton = view.findViewById(buttonId);

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
        } else {
            minValue.setVisibility(View.GONE);
            maxValue.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
            addButton.setOnClickListener(v -> {
                AddThresholdFragment addThresholdFragment = AddThresholdFragment.newInstance(greenHouseId, type);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addThresholdFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

    private Threshold findThresholdByType(List<Threshold> thresholds, MeasurementType type) {
        for (Threshold threshold : thresholds) {
            if (threshold.getType() == type) {
                return threshold;
            }
        }
        return null;
    }

    private void displayHardcodedMeasurements(View view) {
        // Hardcoded latest measurements
        String co2Measurement = "414ppm";
        String humidityMeasurement = "20%";
        String tempMeasurement = "24.5C";
        String lightMeasurement = "80%";

        TextView co2Value = view.findViewById(R.id.co2Value);
        co2Value.setText(co2Measurement);

        TextView humidityValue = view.findViewById(R.id.humidityValue);
        humidityValue.setText(humidityMeasurement);

        TextView tempValue = view.findViewById(R.id.tempValue);
        tempValue.setText(tempMeasurement);

        TextView lightValue = view.findViewById(R.id.lightValue);
        lightValue.setText(lightMeasurement);
    }

    //Helper methods
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
                return "C";
            case LIGHT:
                return "lux";
            default:
                return "";
        }
    }
}
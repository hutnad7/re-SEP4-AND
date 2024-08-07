package com.example.sep4_and.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.List;

import com.example.sep4_and.R;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.ThresholdViewModel;

import java.util.List;
import java.util.Map;

public class AddThresholdFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouse_id";
    private static final String ARG_THRESHOLD_TYPE = "threshold_type";
    private static final String ARG_MIN_VALUE = "min_value";
    private static final String ARG_MAX_VALUE = "max_value";

    private int greenHouseId;
    private MeasurementType thresholdType;
    private Float minValue;
    private Float maxValue;

    private Spinner spinnerThresholdType;
    private EditText editTextMinValue;
    private EditText editTextMaxValue;
    private Button buttonAddThreshold;
    private TextView textViewTitle;
    private TextView textViewSetLevels;
    private ThresholdViewModel thresholdViewModel;

    private Map<MeasurementType, Float> minValues;
    private Map<MeasurementType, Float> maxValues;

    public static AddThresholdFragment newInstance(int greenHouseId, MeasurementType type, Float minValue, Float maxValue) {
        AddThresholdFragment fragment = new AddThresholdFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GREENHOUSE_ID, greenHouseId);
        args.putSerializable(ARG_THRESHOLD_TYPE, type);
        if (minValue != null) args.putFloat(ARG_MIN_VALUE, minValue);
        if (maxValue != null) args.putFloat(ARG_MAX_VALUE, maxValue);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_threshold, container, false);

        spinnerThresholdType = view.findViewById(R.id.spinnerThresholdType);
        editTextMinValue = view.findViewById(R.id.editTextMinValue);
        editTextMaxValue = view.findViewById(R.id.editTextMaxValue);
        buttonAddThreshold = view.findViewById(R.id.buttonAddThreshold);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewSetLevels = view.findViewById(R.id.textViewSetLevels);

        thresholdViewModel = new ViewModelProvider(this).get(ThresholdViewModel.class);

        if (getArguments() != null) {
            greenHouseId = getArguments().getInt(ARG_GREENHOUSE_ID);
            thresholdType = (MeasurementType) getArguments().getSerializable(ARG_THRESHOLD_TYPE);
            if (getArguments().containsKey(ARG_MIN_VALUE)) {
                minValue = getArguments().getFloat(ARG_MIN_VALUE);
            }
            if (getArguments().containsKey(ARG_MAX_VALUE)) {
                maxValue = getArguments().getFloat(ARG_MAX_VALUE);
            }
        }

        ArrayAdapter<MeasurementType> typeAdapter = new ArrayAdapter<MeasurementType>(requireContext(), android.R.layout.simple_spinner_item, MeasurementType.values()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.WHITE);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK);
                return view;
            }
        };
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThresholdType.setAdapter(typeAdapter);
        spinnerThresholdType.setSelection(typeAdapter.getPosition(thresholdType));
        spinnerThresholdType.setEnabled(false);  // Disable the spinner to prevent changing the type

        if (minValue != null) {
            editTextMinValue.setText(String.valueOf(minValue));
        }
        if (maxValue != null) {
            editTextMaxValue.setText(String.valueOf(maxValue));
        }

        loadMeasurementLimits();

        updateMinMaxHints(thresholdType);
        updateTitleText(thresholdType);
        updateLevelText(thresholdType);

        buttonAddThreshold.setOnClickListener(v -> {
            String minValueStr = editTextMinValue.getText().toString();
            String maxValueStr = editTextMaxValue.getText().toString();

            if (TextUtils.isEmpty(minValueStr) || TextUtils.isEmpty(maxValueStr)) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            float minValue = Float.parseFloat(minValueStr);
            float maxValue = Float.parseFloat(maxValueStr);

            if (!validateValues(thresholdType, minValue, maxValue)) {
                return;
            }

            Threshold threshold = new Threshold(thresholdType, minValue, maxValue, greenHouseId);
            thresholdViewModel.insert(threshold);

            // Navigate back to the previous fragment
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void loadMeasurementLimits() {
        minValues = new HashMap<>();
        maxValues = new HashMap<>();

        minValues.put(MeasurementType.CO2, (float) getResources().getInteger(R.integer.min_co2));
        maxValues.put(MeasurementType.CO2, (float) getResources().getInteger(R.integer.max_co2));
        minValues.put(MeasurementType.TEMPERATURE, (float) getResources().getInteger(R.integer.min_temperature));
        maxValues.put(MeasurementType.TEMPERATURE, (float) getResources().getInteger(R.integer.max_temperature));
        minValues.put(MeasurementType.HUMIDITY, (float) getResources().getInteger(R.integer.min_humidity));
        maxValues.put(MeasurementType.HUMIDITY, (float) getResources().getInteger(R.integer.max_humidity));
        minValues.put(MeasurementType.LIGHT, (float) getResources().getInteger(R.integer.min_light));
        maxValues.put(MeasurementType.LIGHT, (float) getResources().getInteger(R.integer.max_light));
    }

    private boolean validateValues(MeasurementType type, float minValue, float maxValue) {
        float minAllowed = minValues.get(type);
        float maxAllowed = maxValues.get(type);

        if (minValue > maxValue) {
            Toast.makeText(getContext(), "Min value cannot be greater than Max value", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (type == MeasurementType.HUMIDITY && (minValue > 100 || maxValue > 100)) {
            Toast.makeText(getContext(), "Humidity values cannot exceed 100%", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (minValue < minAllowed) {
            Toast.makeText(getContext(), "Min value cannot be less than " + minAllowed, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (maxValue > maxAllowed) {
            Toast.makeText(getContext(), "Max value cannot be greater than " + maxAllowed, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateMinMaxHints(MeasurementType selectedType) {
        switch (selectedType) {
            case CO2:
                editTextMinValue.setHint("Min CO2 Value (ppm)");
                editTextMaxValue.setHint("Max CO2 Value (ppm)");
                break;
            case TEMPERATURE:
                editTextMinValue.setHint("Min Temperature (°C)");
                editTextMaxValue.setHint("Max Temperature (°C)");
                break;
            case HUMIDITY:
                editTextMinValue.setHint("Min Humidity (%)");
                editTextMaxValue.setHint("Max Humidity (%)");
                break;
            case LIGHT:
                editTextMinValue.setHint("Min Light (lux)");
                editTextMaxValue.setHint("Max Light (lux)");
                break;
            default:
                editTextMinValue.setHint("Min Value");
                editTextMaxValue.setHint("Max Value");
                break;
        }
    }

    private void updateTitleText(MeasurementType selectedType) {
        switch (selectedType) {
            case CO2:
                textViewTitle.setText("Edit CO2 Levels");
                break;
            case TEMPERATURE:
                textViewTitle.setText("Edit Temperature Levels");
                break;
            case HUMIDITY:
                textViewTitle.setText("Edit Humidity Levels");
                break;
            case LIGHT:
                textViewTitle.setText("Edit Light Levels");
                break;
            default:
                textViewTitle.setText("Edit Threshold");
                break;
        }
    }

    private void updateLevelText(MeasurementType selectedType) {
        switch (selectedType) {
            case CO2:
                textViewSetLevels.setText("Set the CO2 levels");
                break;
            case TEMPERATURE:
                textViewSetLevels.setText("Set the Temperature Levels");
                break;
            case HUMIDITY:
                textViewSetLevels.setText("Set the Humidity Levels");
                break;
            case LIGHT:
                textViewSetLevels.setText("Set the Light Levels");
                break;
            default:
                textViewSetLevels.setText("Set the Thresholds");
                break;
        }
    }
}

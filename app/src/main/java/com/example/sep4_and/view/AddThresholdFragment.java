package com.example.sep4_and.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;

import com.example.sep4_and.R;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.ThresholdViewModel;

import java.util.List;

public class AddThresholdFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouse_id";

    private Spinner spinnerThresholdType;
    private EditText editTextMinValue;
    private EditText editTextMaxValue;
    private Button buttonAddThreshold;
    private ThresholdViewModel thresholdViewModel;
    private int greenHouseId;

    public static AddThresholdFragment newInstance(int greenHouseId) {
        AddThresholdFragment fragment = new AddThresholdFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GREENHOUSE_ID, greenHouseId);
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

        ArrayAdapter<MeasurementType> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, MeasurementType.values());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThresholdType.setAdapter(typeAdapter);

        thresholdViewModel = new ViewModelProvider(this).get(ThresholdViewModel.class);

        if (getArguments() != null) {
            greenHouseId = getArguments().getInt(ARG_GREENHOUSE_ID);
        }

        buttonAddThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeasurementType type = (MeasurementType) spinnerThresholdType.getSelectedItem();
                float minValue = Float.parseFloat(editTextMinValue.getText().toString());
                float maxValue = Float.parseFloat(editTextMaxValue.getText().toString());

                Threshold threshold = new Threshold(type, minValue, maxValue, greenHouseId);
                thresholdViewModel.insert(threshold);
            }
        });

        return view;
    }
}
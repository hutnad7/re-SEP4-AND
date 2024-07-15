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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.list.MeasurementAdapter;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MeasurementsFragment extends Fragment {

    private TextView textViewGreenHouseName;
    private RecyclerView recyclerViewMeasurements;
    private Button buttonBackToMain;
    private MeasurementAdapter measurementAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measurements, container, false);

        textViewGreenHouseName = view.findViewById(R.id.textViewGreenHouseName);
        recyclerViewMeasurements = view.findViewById(R.id.recyclerViewMeasurements);
        buttonBackToMain = view.findViewById(R.id.buttonBackToMain);

        // Hardcoded data for testing
        String greenHouseName = "Test GreenHouse";
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(new Measurement(MeasurementType.TEMPERATURE, 23.5f, new Date(), 1));
        measurements.add(new Measurement(MeasurementType.HUMIDITY, 45.0f, new Date(), 1));
        measurements.add(new Measurement(MeasurementType.CO2, 400.0f, new Date(), 1));
        measurements.add(new Measurement(MeasurementType.LIGHT, 800.0f, new Date(), 1));

        textViewGreenHouseName.setText(greenHouseName);

        measurementAdapter = new MeasurementAdapter(measurements);
        recyclerViewMeasurements.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMeasurements.setAdapter(measurementAdapter);

        buttonBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
package com.example.sep4_and.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.GreenHouseDetailed;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;

public class GreenHouseDetailFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouseId";
    private int greenhouseId;

    private GreenHouseViewModel greenhouseViewModel;

    private TextView textViewGreenhouseLocation;
    private TextView textViewGreenhouseTitle;
    private TextView co2Value;
    private TextView humidityValue;
    private TextView tempValue;
    private TextView lightValue;
    private TextView co2MinValue;
    private TextView co2MaxValue;
    private TextView humidityMinValue;
    private TextView humidityMaxValue;
    private TextView tempMinValue;
    private TextView tempMaxValue;
    private TextView lightMinValue;
    private TextView lightMaxValue;

    public static GreenHouseDetailFragment newInstance(int greenhouseId) {
        GreenHouseDetailFragment fragment = new GreenHouseDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GREENHOUSE_ID, greenhouseId);
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
            greenhouseId = getArguments().getInt(ARG_GREENHOUSE_ID);
        }

        textViewGreenhouseLocation = view.findViewById(R.id.textViewGreenhouseLocation);
        textViewGreenhouseTitle = view.findViewById(R.id.textViewGreenhouseTitle);
        co2Value = view.findViewById(R.id.co2Value);
        humidityValue = view.findViewById(R.id.humidityValue);
        tempValue = view.findViewById(R.id.tempValue);
        lightValue = view.findViewById(R.id.lightValue);
        co2MinValue = view.findViewById(R.id.co2MinValue);
        co2MaxValue = view.findViewById(R.id.co2MaxValue);
        humidityMinValue = view.findViewById(R.id.humidityMinValue);
        humidityMaxValue = view.findViewById(R.id.humidityMaxValue);
        tempMinValue = view.findViewById(R.id.tempMinValue);
        tempMaxValue = view.findViewById(R.id.tempMaxValue);
        lightMinValue = view.findViewById(R.id.lightMinValue);
        lightMaxValue = view.findViewById(R.id.lightMaxValue);

        greenhouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        observeGreenhouseDetails(greenhouseId);
    }

    private void observeGreenhouseDetails(int greenhouseId) {
        greenhouseViewModel.getGreenHouseById(greenhouseId).observe(getViewLifecycleOwner(), new Observer<GreenHouseDetailed>() {
            @Override
            public void onChanged(GreenHouseDetailed greenhouse) {
                if (greenhouse != null) {
                    textViewGreenhouseLocation.setText(greenhouse.getLocation());
                    textViewGreenhouseTitle.setText(greenhouse.getName());
                    co2Value.setText(String.format("%dppm", (int) greenhouse.getCO2()));
                    humidityValue.setText(String.format("%d%%", (int) greenhouse.getHumidity()));
                    tempValue.setText(String.format("%.1f°C", greenhouse.getTemperature()));
                    lightValue.setText(String.format("%d%%", (int) greenhouse.getLight()));
                    co2MinValue.setText(String.format("Min %dppm", (int) greenhouse.getCO2min()));
                    co2MaxValue.setText(String.format("Max %dppm", (int) greenhouse.getCO2max()));
                    humidityMinValue.setText(String.format("Min %d%%", (int) greenhouse.getHumidityMin()));
                    humidityMaxValue.setText(String.format("Max %d%%", (int) greenhouse.getHumidityMax()));
                    tempMinValue.setText(String.format("Min %.1f°C", greenhouse.getTempMin()));
                    tempMaxValue.setText(String.format("Max %.1f°C", greenhouse.getTempMax()));
                    lightMinValue.setText(String.format("Min %d%%", (int) greenhouse.getLightMin()));
                    lightMaxValue.setText(String.format("Max %d%%", (int) greenhouse.getLightMax()));
                }
            }
        });
    }
}

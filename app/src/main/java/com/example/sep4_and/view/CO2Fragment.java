package com.example.sep4_and.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.CO2Measurement;
import com.example.sep4_and.viewmodel.GreenhouseViewModel;

public class CO2Fragment extends Fragment {
    private GreenhouseViewModel viewModel;
    private TextView co2PercentageTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_co2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize TextViews
        co2PercentageTextView = view.findViewById(R.id.co2PercentageTextView);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(GreenhouseViewModel.class);

        // Observe the LiveData from the ViewModel for CO2 measurement
        viewModel.getCO2Measurement().observe(getViewLifecycleOwner(), new Observer<CO2Measurement>() {
            @Override
            public void onChanged(CO2Measurement co2Measurement) {
                if (co2Measurement != null) {
                    // Update CO2 percentage TextView
                    String co2Value = String.format("%d ppm", co2Measurement.getValue());
                    co2PercentageTextView.setText(co2Value);
                }
            }
        });

        // Handle click on CO2 box
        LinearLayout co2Box = view.findViewById(R.id.co2Box);
        co2Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to detailed CO2 measurement page
                Intent intent = new Intent(getActivity(), CO2DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}

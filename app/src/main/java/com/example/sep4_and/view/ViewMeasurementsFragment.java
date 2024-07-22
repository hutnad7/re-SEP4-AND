package com.example.sep4_and.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sep4_and.R;
import com.example.sep4_and.list.MeasurementAdapter;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.viewmodel.MeasurementViewModel;

import java.util.List;
public class ViewMeasurementsFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouse_id";

    private MeasurementViewModel measurementViewModel;
    private RecyclerView recyclerView;
    private MeasurementAdapter adapter;
    private int greenHouseId;

    public static ViewMeasurementsFragment newInstance(int greenHouseId) {
        ViewMeasurementsFragment fragment = new ViewMeasurementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GREENHOUSE_ID, greenHouseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_measurements, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMeasurements);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MeasurementAdapter();
        recyclerView.setAdapter(adapter);

        measurementViewModel = new ViewModelProvider(this).get(MeasurementViewModel.class);

        if (getArguments() != null) {
            greenHouseId = getArguments().getInt(ARG_GREENHOUSE_ID);
        }

        measurementViewModel.getMeasurementsForGreenHouse(greenHouseId).observe(getViewLifecycleOwner(), new Observer<List<Measurement>>() {
            @Override
            public void onChanged(List<Measurement> measurements) {
                adapter.setMeasurements(measurements);
            }
        });

        return view;
    }
}
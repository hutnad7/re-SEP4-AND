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
import com.example.sep4_and.list.ThresholdAdapter;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.viewmodel.ThresholdViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
public class ThresholdListFragment extends Fragment {

    private static final String ARG_GREENHOUSE_ID = "greenhouse_id";

    private int greenHouseId;
    private ThresholdViewModel thresholdViewModel;
    private RecyclerView recyclerView;
    private ThresholdAdapter adapter;

    public static ThresholdListFragment newInstance(int greenHouseId) {
        ThresholdListFragment fragment = new ThresholdListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GREENHOUSE_ID, greenHouseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threshold_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewThresholds);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ThresholdAdapter(threshold -> {
            thresholdViewModel.delete(threshold);
        });
        recyclerView.setAdapter(adapter);


        if (getArguments() != null) {
            greenHouseId = getArguments().getInt(ARG_GREENHOUSE_ID);
        }

        thresholdViewModel = new ViewModelProvider(this).get(ThresholdViewModel.class);
        thresholdViewModel.getThresholdsForGreenHouse(greenHouseId).observe(getViewLifecycleOwner(), new Observer<List<Threshold>>() {
            @Override
            public void onChanged(List<Threshold> thresholds) {
                adapter.setThresholds(thresholds);
            }
        });

        return view;
    }
}

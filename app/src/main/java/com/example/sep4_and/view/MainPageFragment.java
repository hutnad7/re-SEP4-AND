package com.example.sep4_and.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.list.GreenHouseAdapter;
import com.example.sep4_and.utils.TokenManager;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;



public class MainPageFragment extends Fragment {

    private RecyclerView recyclerView;
    private GreenHouseAdapter adapter;
    private GreenHouseViewModel greenhouseViewModel;
    private TextView greenhouseCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.greenhouseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView helloText = view.findViewById(R.id.helloText);
        FrameLayout notificationIcon = view.findViewById(R.id.notificationIcon);
        FrameLayout settingsIcon = view.findViewById(R.id.settingsIcon);
        FrameLayout logoutButton = view.findViewById(R.id.logoutButton);
        greenhouseCount = view.findViewById(R.id.greenhouseCount);
        FrameLayout addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {

        });


        logoutButton.setOnClickListener(v -> logout());

        greenhouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        observeGreenhouses();
    }

    private void observeGreenhouses() {
        greenhouseViewModel.getGreenhouses().observe(getViewLifecycleOwner(), greenhouses -> {
            if (greenhouses != null) {
                adapter = new GreenHouseAdapter(greenhouses, this::navigateToDetailFragment);
                recyclerView.setAdapter(adapter);
                greenhouseCount.setText("You got " + greenhouses.size() + " greenhouses");
            }
        });
    }

    private void navigateToDetailFragment(int greenhouseId) {
        GreenHouseDetailFragment fragment = GreenHouseDetailFragment.newInstance(greenhouseId);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void logout() {
        TokenManager.clearToken();
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        requireActivity().finish();


    }
}
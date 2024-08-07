package com.example.sep4_and.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;

public class AddGreenHouseFragment extends Fragment {
    private GreenHouseViewModel greenHouseViewModel;
    private UserViewModel userViewModel;
    private EditText editTextName;
    private EditText editTextLocation;
    private Button buttonAdd;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("AddGreenHouseFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_add_greenhouse, container, false);

        editTextName = view.findViewById(R.id.editTextGreenHouseName);
        editTextLocation = view.findViewById(R.id.editTextGreenHouseLocation);
        buttonAdd = view.findViewById(R.id.buttonAddGreenHouse);

        buttonAdd.setOnClickListener(v -> {
            Log.d("AddGreenHouseFragment", "Add button clicked");
            addGreenHouse();
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("AddGreenHouseFragment", "onActivityCreated called");
        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe current user here
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), currentUser -> {
            if (currentUser != null) {
                Log.d("AddGreenHouseFragment", "Current user ID: " + currentUser.getId());
                this.currentUser = currentUser; // Store the current user
            } else {
                Log.d("AddGreenHouseFragment", "No current user found");
            }
        });
    }

    private void addGreenHouse() {
        Log.d("AddGreenHouseFragment", "addGreenHouse method called");
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location)) {
            Log.d("AddGreenHouseFragment", "Name or location is empty");
            Toast.makeText(getActivity(), "Please enter both name and location", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentUser != null) {
            Log.d("AddGreenHouseFragment", "Current user ID: " + currentUser.getId());
            GreenHouse greenHouse = new GreenHouse(name, location, currentUser.getId());
            greenHouseViewModel.insert(greenHouse).observe(getViewLifecycleOwner(), greenHouseId -> {
                if (greenHouseId != null) {
                    Log.d("AddGreenHouseFragment", "GreenHouse inserted with ID: " + greenHouseId);
                    Toast.makeText(getActivity(), "GreenHouse added and paired with user", Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                    editTextLocation.setText("");
                    getParentFragmentManager().popBackStack();
                } else {
                    Log.d("AddGreenHouseFragment", "Failed to insert GreenHouse");
                    Toast.makeText(getActivity(), "Failed to add GreenHouse", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("AddGreenHouseFragment", "Current User is null");
            Toast.makeText(getActivity(), "No current user", Toast.LENGTH_SHORT).show();
        }
    }
}
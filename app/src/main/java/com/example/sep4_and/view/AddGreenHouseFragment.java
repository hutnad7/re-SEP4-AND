package com.example.sep4_and.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;

public class AddGreenHouseFragment extends Fragment {
    private GreenHouseViewModel greenHouseViewModel;
    private EditText editTextName;
    private EditText editTextLocation;
    private Button buttonAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_greenhouse, container, false);

        editTextName = view.findViewById(R.id.editTextGreenHouseName);
        editTextLocation = view.findViewById(R.id.editTextGreenHouseLocation);
        buttonAdd = view.findViewById(R.id.buttonAddGreenHouse);

        buttonAdd.setOnClickListener(v -> addGreenHouse());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
    }

    private void addGreenHouse() {
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location)) {
            Toast.makeText(getActivity(), "Please enter both name and location", Toast.LENGTH_SHORT).show();
            return;
        }

        GreenHouse greenHouse = new GreenHouse(name, location);
//        greenHouseViewModel.insert(greenHouse);
        Toast.makeText(getActivity(), "GreenHouse added", Toast.LENGTH_SHORT).show();
        editTextName.setText("");
        editTextLocation.setText("");
    }
}

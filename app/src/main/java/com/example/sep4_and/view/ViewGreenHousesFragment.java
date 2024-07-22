package com.example.sep4_and.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.list.GreenHouseAdapter;
import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;
import com.example.sep4_and.model.DbCrossReference.GreenHouseWithUsers;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.MeasurementViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.util.Date;
import java.util.List;



public class ViewGreenHousesFragment extends Fragment {

/*public class ViewGreenHousesFragment extends Fragment implements GreenHouseAdapter.OnPairButtonClickListener {


    private GreenHouseViewModel greenHouseViewModel;
    private MeasurementViewModel measurementViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private GreenHouseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_greenhouses, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGreenHouses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GreenHouseAdapter(
                this::onPairButtonClick,
                this::onAddThresholdButtonClick,
                this::onViewMeasurementsButtonClick
        );
        recyclerView.setAdapter(adapter);

        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        measurementViewModel = new ViewModelProvider(this).get(MeasurementViewModel.class);

        greenHouseViewModel.getAllGreenHousesWithUsers().observe(getViewLifecycleOwner(), new Observer<List<GreenHouseWithUsers>>() {
            @Override
            public void onChanged(List<GreenHouseWithUsers> greenHousesWithUsers) {
                adapter.setGreenHousesWithUsers(greenHousesWithUsers);
            }
        });

        return view;
    }




    public void onPairButtonClick(GreenHouse greenHouse) {
        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (!users.isEmpty()) {
                    User firstUser = users.get(0); // Get the first user in the database
                    GreenHouseUserCrossRef crossRef = new GreenHouseUserCrossRef();
                    crossRef.greenHouseId = greenHouse.getId();
                    crossRef.userId = firstUser.getId();
                    greenHouseViewModel.insertGreenHouseUserCrossRef(crossRef); // Add the relationship
                }
            }
        });
    }
    private void onAddThresholdButtonClick(GreenHouse greenHouse) {
        AddThresholdFragment addThresholdFragment = AddThresholdFragment.newInstance(greenHouse.getId());
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, addThresholdFragment)
                .addToBackStack(null)
                .commit();
    }
    private void onViewMeasurementsButtonClick(GreenHouse greenHouse) {
        measurementViewModel.getMeasurementsForGreenHouse(greenHouse.getId()).observe(getViewLifecycleOwner(), new Observer<List<Measurement>>() {
            @Override
            public void onChanged(List<Measurement> measurements) {
                if (measurements == null || measurements.isEmpty()) {
                    // Add fictitious measurement
                    //TODO: Remove when connected to API
                    Measurement fictitiousMeasurement = new Measurement(MeasurementType.TEMPERATURE, 0.0f, new Date(), greenHouse.getId());
                    measurementViewModel.insert(fictitiousMeasurement);
                } else {
                    // Navigate to measurements fragment
                    ViewMeasurementsFragment viewMeasurementsFragment = ViewMeasurementsFragment.newInstance(greenHouse.getId());
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, viewMeasurementsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
}
}

}*/


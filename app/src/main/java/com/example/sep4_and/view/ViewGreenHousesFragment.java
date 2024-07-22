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
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.util.List;

/*public class ViewGreenHousesFragment extends Fragment implements GreenHouseAdapter.OnPairButtonClickListener {

    private GreenHouseViewModel greenHouseViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private GreenHouseAdapter greenHouseAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_greenhouses, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGreenHouses);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        greenHouseAdapter = new GreenHouseAdapter(this);
        recyclerView.setAdapter(greenHouseAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        greenHouseViewModel = new ViewModelProvider(this).get(GreenHouseViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        greenHouseViewModel.getAllGreenHousesWithUsers().observe(getViewLifecycleOwner(), new Observer<List<GreenHouseWithUsers>>() {
            @Override
            public void onChanged(List<GreenHouseWithUsers> greenHousesWithUsers) {
                greenHouseAdapter.setGreenHousesWithUsers(greenHousesWithUsers);
            }
        });
    }

    @Override
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
}*/
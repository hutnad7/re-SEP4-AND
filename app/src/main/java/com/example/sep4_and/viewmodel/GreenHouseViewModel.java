package com.example.sep4_and.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;
import com.example.sep4_and.model.DbCrossReference.GreenHouseWithUsers;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.repository.GreenHouseRepository;

import java.util.List;

public class GreenHouseViewModel extends AndroidViewModel {
    private GreenHouseRepository repository;
    private LiveData<List<GreenHouseWithUsers>> allGreenHousesWithUsers;
    private LiveData<List<GreenHouse>> allGreenHouses;

    public GreenHouseViewModel(Application application) {
        super(application);
        repository = new GreenHouseRepository(application);
        allGreenHousesWithUsers = repository.getGreenHousesWithUsers();
        allGreenHouses = repository.getAllGreenHouses();
    }

    public LiveData<List<GreenHouseWithUsers>> getAllGreenHousesWithUsers() {
        return allGreenHousesWithUsers;
    }

    public void insert(GreenHouse greenHouse) {
        repository.insert(greenHouse);
    }

    public void delete(GreenHouse greenHouse) {repository.delete(greenHouse);}

    public LiveData<List<GreenHouse>> getAllGreenHouses() {
        return allGreenHouses;
    }
    public LiveData<GreenHouse> getGreenHouseById(int greenHouseId) {
        return repository.getGreenHouseById(greenHouseId);
    }
    public void insertGreenHouseUserCrossRef(GreenHouseUserCrossRef crossRef) {
        repository.insertGreenHouseUserCrossRef(crossRef);
    }
}
package com.example.sep4_and.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.repository.GreenHouseRepository;

import java.util.List;

public class GreenHouseViewModel extends AndroidViewModel {
    private GreenHouseRepository repository;

    public GreenHouseViewModel(@NonNull Application application) {
        super(application);
        repository = new GreenHouseRepository(application);
    }

    public LiveData<Long> insert(GreenHouse greenHouse) {
        return repository.insert(greenHouse);
    }

    public LiveData<List<GreenHouse>> getGreenHousesByUserId(int userId) {
        return repository.getGreenHousesByUserId(userId);
    }

    public void delete(GreenHouse greenHouse) {
        repository.delete(greenHouse);
    }

    public LiveData<GreenHouse> getGreenHouseById(int greenHouseId) {
        return repository.getGreenHouseById(greenHouseId);
    }
}
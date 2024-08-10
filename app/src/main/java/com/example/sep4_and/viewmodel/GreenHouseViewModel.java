package com.example.sep4_and.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.repository.GreenHouseRepository;
import com.example.sep4_and.repository.MeasurementRepository;

import java.util.List;

public class GreenHouseViewModel extends AndroidViewModel {
    private GreenHouseRepository repository;
    //Should separate at fragment level to keep (SRP). Not the worse since the two are closely related
    //TLDR: Keep as is = bloat ; Separate = Boilerplate ; Boilerplate>bloat
    private MeasurementRepository measurementRepository;

    public GreenHouseViewModel(@NonNull Application application) {
        super(application);
        repository = new GreenHouseRepository(application);
        measurementRepository = new MeasurementRepository(application);
    }

    public LiveData<Long> insert(GreenHouse greenHouse) {
        return repository.insert(greenHouse);
    }

    public LiveData<List<GreenHouse>> getGreenHousesByUserId(int userId) {
        return repository.getGreenHousesByUserId(userId);
    }
    public LiveData<Measurement> getLatestMeasurementForType(int greenHouseId, MeasurementType type) {
        return measurementRepository.getLatestMeasurementForType(greenHouseId, type);
    }
    public void delete(GreenHouse greenHouse) {
        repository.delete(greenHouse);
    }

    public LiveData<GreenHouse> getGreenHouseById(int greenHouseId) {
        return repository.getGreenHouseById(greenHouseId);
    }
}
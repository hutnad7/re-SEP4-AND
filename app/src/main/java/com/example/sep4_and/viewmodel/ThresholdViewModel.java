package com.example.sep4_and.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.repository.ThresholdRepository;

import java.util.List;

public class ThresholdViewModel extends AndroidViewModel {
    private ThresholdRepository repository;
    private LiveData<List<Threshold>> thresholds; //Not used, might get used, keeping just in case

    public ThresholdViewModel(@NonNull Application application) {
        super(application);
        repository = new ThresholdRepository(application);
    }

    public void insert(Threshold threshold) {
        repository.insert(threshold);
    }

    public void update(Threshold threshold) {
        repository.update(threshold);
    }

    public LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId) {
        return repository.getThresholdsForGreenHouse(greenHouseId);
    }
    public LiveData<Threshold> getThresholdForGreenHouseByTypeLiveData(int greenHouseId, MeasurementType type) {
        return repository.getThresholdForGreenHouseByTypeLiveData(greenHouseId, type);
    }
    public void delete(Threshold threshold) {
        repository.delete(threshold);
    }
}
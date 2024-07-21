package com.example.sep4_and.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.repository.ThresholdRepository;

import java.util.List;

public class ThresholdViewModel extends AndroidViewModel {
    private ThresholdRepository repository;
    private LiveData<List<Threshold>> thresholds;

    public ThresholdViewModel(Application application) {
        super(application);
        repository = new ThresholdRepository(application);
    }

    public void insert(Threshold threshold) {
        repository.insert(threshold);
    }

    public LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId) {
        return repository.getThresholdsForGreenHouse(greenHouseId);
    }
}
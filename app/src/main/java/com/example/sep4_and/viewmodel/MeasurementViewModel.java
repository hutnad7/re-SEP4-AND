package com.example.sep4_and.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.repository.MeasurementRepository;

import java.util.List;

public class MeasurementViewModel extends AndroidViewModel {
    private MeasurementRepository repository;
    private LiveData<List<Measurement>> measurements;

    public MeasurementViewModel(Application application) {
        super(application);
        repository = new MeasurementRepository(application);
    }

    public void insert(Measurement measurement) {
        repository.insert(measurement);
    }

    public LiveData<List<Measurement>> getMeasurementsForGreenHouse(int greenHouseId) {
        return repository.getMeasurementsForGreenHouse(greenHouseId);
    }

    //Long name but very fitting
    public LiveData<List<Measurement>> getMeasurementsForGreenHouseWithinDateRange(int greenHouseId, long startDate, long endDate) {
        return repository.getMeasurementsForGreenHouseWithinDateRange(greenHouseId, startDate, endDate);
    }
}
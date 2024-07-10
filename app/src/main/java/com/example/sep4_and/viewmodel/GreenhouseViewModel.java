package com.example.sep4_and.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sep4_and.model.CO2Measurement;
import com.example.sep4_and.repository.GreenhouseRepository;

public class GreenhouseViewModel extends ViewModel {
    private GreenhouseRepository repository;
    private LiveData<CO2Measurement> co2Measurement;

    public GreenhouseViewModel() {
        repository = new GreenhouseRepository();
        co2Measurement = repository.getCO2Measurement();
    }

    public LiveData<CO2Measurement> getCO2Measurement() {
        return co2Measurement;
    }

    public void refreshCO2Measurement() {

    }
}

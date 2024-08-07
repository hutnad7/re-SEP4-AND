package com.example.sep4_and.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.ThresholdDao;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThresholdRepository {
    private ThresholdDao thresholdDao;
    private ExecutorService executorService;

    public ThresholdRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        thresholdDao = db.thresholdDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Threshold threshold) {
        executorService.execute(() -> thresholdDao.insert(threshold));
    }
    public void update(Threshold threshold) {
        executorService.execute(() -> thresholdDao.update(threshold));
    }

    public LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId) {
        return thresholdDao.getThresholdsForGreenHouse(greenHouseId);
    }

    public LiveData<Threshold> getThresholdForGreenHouseByTypeLiveData(int greenHouseId, MeasurementType type) {
        MutableLiveData<Threshold> thresholdLiveData = new MutableLiveData<>();
        executorService.execute(() -> {
            Threshold threshold = thresholdDao.getThresholdForGreenHouseByType(greenHouseId, type);
            thresholdLiveData.postValue(threshold);
        });
        return thresholdLiveData;
    }
    public void delete(Threshold threshold) {
        executorService.execute(() -> thresholdDao.delete(threshold));
    }
}
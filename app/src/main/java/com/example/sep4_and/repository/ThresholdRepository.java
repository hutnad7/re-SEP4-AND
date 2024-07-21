package com.example.sep4_and.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.ThresholdDao;
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

    public LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId) {
        return thresholdDao.getThresholdsForGreenHouse(greenHouseId);
    }
}
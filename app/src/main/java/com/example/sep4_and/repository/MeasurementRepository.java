package com.example.sep4_and.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.MeasurementDao;
import com.example.sep4_and.model.Measurement;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeasurementRepository {
    private MeasurementDao measurementDao;
    private ExecutorService executorService;

    public MeasurementRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        measurementDao = db.measurementDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Measurement measurement) {
        executorService.execute(() -> measurementDao.insert(measurement));
    }

    public LiveData<List<Measurement>> getMeasurementsForGreenHouse(int greenHouseId) {
        return measurementDao.getMeasurementsForGreenHouse(greenHouseId);
    }
}
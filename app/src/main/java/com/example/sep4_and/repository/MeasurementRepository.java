package com.example.sep4_and.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.MeasurementDao;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.MeasurementApi;
import com.example.sep4_and.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeasurementRepository {
    private MeasurementDao measurementDao;
    private MeasurementApi measurementApi;
    private ExecutorService executorService;

    public MeasurementRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        measurementDao = db.measurementDao();
        measurementApi = RetrofitInstance.getClient("").create(MeasurementApi.class);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Measurement measurement) {
        executorService.execute(() -> {
            if (Config.isUseApi()) {
                measurementApi.createMeasurement(measurement).observeForever(apiResult -> {
                    // handle API result
                });
            } else {
                measurementDao.insert(measurement);
            }
        });
    }

    public LiveData<List<Measurement>> getMeasurementsForGreenHouse(int greenHouseId) {
        if (Config.isUseApi()) {
            return measurementApi.getMeasurements(greenHouseId);
        } else {
            return measurementDao.getMeasurementsForGreenHouse(greenHouseId);
        }
    }

    public LiveData<Measurement> getLatestMeasurementForType(int greenHouseId, MeasurementType type) {
        if (Config.isUseApi()) {
            // Implement API call
            return new MutableLiveData<>();
        } else {
            return measurementDao.getLatestMeasurementForType(greenHouseId, type);
        }
    }

    public LiveData<List<Measurement>> getMeasurementsForGreenHouseWithinDateRange(int greenHouseId, long startDate, long endDate) {
        if (Config.isUseApi()) {
            // Implement API call
            return new MutableLiveData<>(new ArrayList<>());
        } else {
            return measurementDao.getMeasurementsForGreenHouseWithinDateRange(greenHouseId, startDate, endDate);
        }
    }
}
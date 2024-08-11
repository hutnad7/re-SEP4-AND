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
                    if (Config.isEchoToLocalDatabase() && apiResult != null) {
                        measurementDao.insert(measurement); // Echo to local DB
                    }
                });
            } else {
                measurementDao.insert(measurement);
            }
        });
    }

    public LiveData<List<Measurement>> getMeasurementsForGreenHouse(int greenHouseId) {
        if (Config.isUseApi()) {
            MutableLiveData<List<Measurement>> result = new MutableLiveData<>();
            measurementApi.getMeasurements(greenHouseId).observeForever(measurements -> {
                result.postValue(measurements);
                if (Config.isEchoToLocalDatabase()) {
                    // No need to echo for view only data
                }
            });
            return result;
        } else {
            return measurementDao.getMeasurementsForGreenHouse(greenHouseId);
        }
    }

    public LiveData<Measurement> getLatestMeasurementForType(int greenHouseId, MeasurementType type) {
        if (Config.isUseApi()) {
            MutableLiveData<Measurement> result = new MutableLiveData<>();
            measurementApi.getLatestMeasurementForType(greenHouseId, type).observeForever(measurement -> {
                result.postValue(measurement);
                if (Config.isEchoToLocalDatabase() && measurement != null) {
                    // No need to echo for view only data
                }
            });
            return result;
        } else {
            return measurementDao.getLatestMeasurementForType(greenHouseId, type);
        }
    }

    public LiveData<List<Measurement>> getMeasurementsForGreenHouseWithinDateRange(int greenHouseId, long startDate, long endDate) {
        if (Config.isUseApi()) {
            MutableLiveData<List<Measurement>> result = new MutableLiveData<>();
            measurementApi.getMeasurementsForGreenHouseWithinDateRange(greenHouseId, startDate, endDate).observeForever(measurements -> {
                result.postValue(measurements);
                if (Config.isEchoToLocalDatabase()) {
                    // No need to echo for view only data
                }
            });
            return result;
        } else {
            return measurementDao.getMeasurementsForGreenHouseWithinDateRange(greenHouseId, startDate, endDate);
        }
    }
}
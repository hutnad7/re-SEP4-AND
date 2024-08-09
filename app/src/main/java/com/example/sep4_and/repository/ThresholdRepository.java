package com.example.sep4_and.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4_and.BuildConfig;
import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.ThresholdDao;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.ThresholdApi;
import com.example.sep4_and.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThresholdRepository {
    private ThresholdDao thresholdDao;
    private ThresholdApi thresholdApi;
    private ExecutorService executorService;

    public ThresholdRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        thresholdDao = db.thresholdDao();
        thresholdApi = RetrofitInstance.getClient(BuildConfig.BASE_URL).create(ThresholdApi.class);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Threshold threshold) {
        executorService.execute(() -> {
            if (Config.isUseApi()) {
                thresholdApi.createThreshold(threshold).observeForever(apiResponse -> {
                    if (Config.isEchoToLocalDatabase() && apiResponse != null) {
                        thresholdDao.insert(threshold); // Echo to local DB
                    }
                });
            } else {
                thresholdDao.insert(threshold);
            }
        });
    }

    public void update(Threshold threshold) {
        executorService.execute(() -> {
            if (Config.isUseApi()) {
                thresholdApi.updateThreshold(threshold.getId(), threshold).observeForever(apiResponse -> {
                    if (Config.isEchoToLocalDatabase() && apiResponse != null) {
                        thresholdDao.update(threshold); // Echo to local DB
                    }
                });
            } else {
                thresholdDao.update(threshold);
            }
        });
    }

    public LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId) {
        if (Config.isUseApi()) {
            MutableLiveData<List<Threshold>> result = new MutableLiveData<>();
            thresholdApi.getThresholdsForGreenHouse(greenHouseId).observeForever(thresholds -> {
                result.postValue(thresholds);
                if (Config.isEchoToLocalDatabase()) {
                    // No need to echo for view only data
                }
            });
            return result;
        } else {
            return thresholdDao.getThresholdsForGreenHouse(greenHouseId);
        }
    }

    public LiveData<Threshold> getThresholdForGreenHouseByTypeLiveData(int greenHouseId, MeasurementType type) {
        if (Config.isUseApi()) {
            MutableLiveData<Threshold> result = new MutableLiveData<>();
            thresholdApi.getThresholdForGreenHouseByType(greenHouseId, type).observeForever(threshold -> {
                result.postValue(threshold);
                if (Config.isEchoToLocalDatabase()) {
                    executorService.execute(() -> thresholdDao.insert(threshold)); // Echo to local DB
                }
            });
            return result;
        } else {
            MutableLiveData<Threshold> thresholdLiveData = new MutableLiveData<>();
            executorService.execute(() -> {
                Threshold threshold = thresholdDao.getThresholdForGreenHouseByType(greenHouseId, type);
                thresholdLiveData.postValue(threshold);
            });
            return thresholdLiveData;
        }
    }

    public void delete(Threshold threshold) {
        executorService.execute(() -> {
            if (Config.isUseApi()) {
                thresholdApi.deleteThreshold(threshold.getId()).observeForever(apiResponse -> {
                    if (Config.isEchoToLocalDatabase() && apiResponse != null) {
                        thresholdDao.delete(threshold); // Echo to local DB
                    }
                });
            } else {
                thresholdDao.delete(threshold);
            }
        });
    }
}
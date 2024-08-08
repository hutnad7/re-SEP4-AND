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
                    // handle API result if needed
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
                    // handle API result if needed
                });
            } else {
                thresholdDao.update(threshold);
            }
        });
    }

    public LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId) {
        if (Config.isUseApi()) {
            return thresholdApi.getThresholdsForGreenHouse(greenHouseId);
        } else {
            return thresholdDao.getThresholdsForGreenHouse(greenHouseId);
        }
    }

    public LiveData<Threshold> getThresholdForGreenHouseByTypeLiveData(int greenHouseId, MeasurementType type) {
        if (Config.isUseApi()) {
            return thresholdApi.getThresholdForGreenHouseByType(greenHouseId, type);
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
                    // handle API result if needed
                });
            } else {
                thresholdDao.delete(threshold);
            }
        });
    }
}
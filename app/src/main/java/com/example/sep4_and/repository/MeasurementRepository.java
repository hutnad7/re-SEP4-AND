package com.example.sep4_and.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sep4_and.BuildConfig;
import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.MeasurementDao;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.MeasurementApi;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementRepository {
    private MeasurementDao measurementDao;
    private MeasurementApi measurementApi;
    private ExecutorService executorService;

    public MeasurementRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        measurementDao = db.measurementDao();
        measurementApi = RetrofitInstance.getClient(BuildConfig.BASE_URL).create(MeasurementApi.class);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Measurement measurement) {
        executorService.execute(() -> {
            // Insert into local database
            measurementDao.insert(measurement);
            // Make network call to insert into backend
            measurementApi.createMeasurement(measurement).enqueue(new Callback<Measurement>() {
                @Override
                public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                    if (response.isSuccessful()) {
                        // Handle successful response if needed
                    }
                }

                @Override
                public void onFailure(Call<Measurement> call, Throwable t) {
                    // Handle failure
                }
            });
        });
    }

    public LiveData<List<Measurement>> getMeasurementsForGreenHouse(int greenHouseId) {
        // Make network call to fetch measurements from backend
        measurementApi.getMeasurements(greenHouseId).enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                if (response.isSuccessful()) {
                    // Insert fetched measurements into local database
                    executorService.execute(() -> {
                        List<Measurement> measurements = response.body();
                        if (measurements != null) {
                            for (Measurement measurement : measurements) {
                                measurementDao.insert(measurement);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                // Handle failure
            }
        });

        // Return LiveData from local database
        return measurementDao.getMeasurementsForGreenHouse(greenHouseId);
    }
}
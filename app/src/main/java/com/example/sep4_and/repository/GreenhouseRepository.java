package com.example.sep4_and.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4_and.model.CO2Measurement;
import com.example.sep4_and.network.GreenhouseApi;
import com.example.sep4_and.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GreenhouseRepository {
    private GreenhouseApi greenhouseApi;

    public GreenhouseRepository() {
        greenhouseApi = RetrofitInstance.getRetrofitInstance().create(GreenhouseApi.class);
    }

    public LiveData<CO2Measurement> getCO2Measurement() {
        MutableLiveData<CO2Measurement> data = new MutableLiveData<>();
        greenhouseApi.getCO2Measurement().enqueue(new Callback<CO2Measurement>() {
            @Override
            public void onResponse(Call<CO2Measurement> call, Response<CO2Measurement> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CO2Measurement> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}

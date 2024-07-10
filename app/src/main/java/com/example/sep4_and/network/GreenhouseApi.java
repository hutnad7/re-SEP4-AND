package com.example.sep4_and.network;

import com.example.sep4_and.model.CO2Measurement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GreenhouseApi {
    @GET("co2_measurements")
    Call<CO2Measurement> getCO2Measurement();
}

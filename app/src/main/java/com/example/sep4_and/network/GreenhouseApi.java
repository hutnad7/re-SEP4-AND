package com.example.sep4_and.network;

import com.example.sep4_and.model.CO2Measurement;
import com.example.sep4_and.model.HistoricalMeasurement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GreenhouseApi {
    @GET("co2_measurements")
    Call<CO2Measurement> getCO2Measurement();

    @GET("historicalMeasurements")
    Call<List<HistoricalMeasurement>> getHistoricalMeasurements(
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );
}

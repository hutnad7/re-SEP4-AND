package com.example.sep4_and.network.api;

import com.example.sep4_and.model.Measurement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MeasurementApi {
    @POST("measurements")
    Call<Measurement> createMeasurement(@Body Measurement measurement);

    @GET("measurements/{greenHouseId}")
    Call<List<Measurement>> getMeasurements(@Path("greenHouseId") int greenHouseId);
}
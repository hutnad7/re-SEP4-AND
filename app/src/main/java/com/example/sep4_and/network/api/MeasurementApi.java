package com.example.sep4_and.network.api;

import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MeasurementApi {

    @POST("measurements")
    LiveData<Measurement> createMeasurement(@Body Measurement measurement);

    @GET("measurements/{greenHouseId}")
    LiveData<List<Measurement>> getMeasurements(@Path("greenHouseId") int greenHouseId);

    @GET("measurements/{greenHouseId}/latest/{type}")
    LiveData<Measurement> getLatestMeasurementForType(
            @Path("greenHouseId") int greenHouseId,
            @Path("type") MeasurementType type
    );

    @GET("measurements/{greenHouseId}/range")
    LiveData<List<Measurement>> getMeasurementsForGreenHouseWithinDateRange(
            @Path("greenHouseId") int greenHouseId,
            @Query("startDate") long startDate,
            @Query("endDate") long endDate
    );
}
package com.example.sep4_and.network.api;

import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThresholdApi {
    @POST("thresholds")
    LiveData<Threshold> createThreshold(@Body Threshold threshold);

    @PUT("thresholds/{id}")
    LiveData<Threshold> updateThreshold(@Path("id") int id, @Body Threshold threshold);

    @GET("greenhouse/{greenHouseId}/thresholds")
    LiveData<List<Threshold>> getThresholdsForGreenHouse(@Path("greenHouseId") int greenHouseId);

    @GET("greenhouse/{greenHouseId}/thresholds/{type}")
    LiveData<Threshold> getThresholdForGreenHouseByType(@Path("greenHouseId") int greenHouseId, @Path("type") MeasurementType type);

    @DELETE("thresholds/{id}")
    LiveData<Void> deleteThreshold(@Path("id") int id);
}
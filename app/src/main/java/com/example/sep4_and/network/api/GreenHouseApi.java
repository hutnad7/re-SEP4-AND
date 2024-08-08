package com.example.sep4_and.network.api;

import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.GreenHouse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GreenHouseApi {
    @POST("greenhouse")
    LiveData<GreenHouse> createGreenHouse(@Body GreenHouse greenHouse);

    @DELETE("greenhouse/{id}")
    LiveData<Void> deleteGreenHouse(@Path("id") int id);

    @GET("user/{id}/greenhouse")
    LiveData<List<GreenHouse>> getUserGreenhouses(@Path("id") int userId);

    @GET("greenhouse/{id}")
    LiveData<GreenHouse> getGreenhouseById(@Path("id") int id);
}
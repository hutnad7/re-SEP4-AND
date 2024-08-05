package com.example.sep4_and.network.api;

import com.example.sep4_and.model.GreenHouseDetailed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GreenHouseApi {
    @GET("user/{id}/greenhouse")
    Call<List<GreenHouseDetailed>> getUserGreenhouses(@Path("id") int userId);

    @GET("greenhouse/{id}")
    Call<GreenHouseDetailed> getGreenhouseById(@Path("id") int id);
}

package com.example.sep4_and.network.api;

import com.example.sep4_and.model.User;
import com.example.sep4_and.network.requests.LoginRequest;
import com.example.sep4_and.network.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("users/register")
    Call<Void> register(@Body User user);

    @POST("users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}

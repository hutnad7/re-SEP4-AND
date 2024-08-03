package com.example.sep4_and.network.api;

import com.example.sep4_and.model.User;
import com.example.sep4_and.network.requests.LoginRequest;
import com.example.sep4_and.network.requests.RegisterRequest;
import com.example.sep4_and.network.responses.LoginResponse;
import com.example.sep4_and.network.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("user")
    Call<User> register(@Body RegisterRequest registerRequest);

    @POST("user/login")
    Call<User> login(@Body LoginRequest loginRequest);
}

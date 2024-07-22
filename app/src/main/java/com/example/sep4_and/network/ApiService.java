package com.example.sep4_and.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.example.sep4_and.model.AuthRequest;
import com.example.sep4_and.model.AuthResponse;
import com.example.sep4_and.model.User;

import java.util.List;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);


    @POST("oauth/token")
    Call<AuthResponse> login(@Body AuthRequest authRequest);

    @POST("users")
    Call<User> register(@Body User user);

}

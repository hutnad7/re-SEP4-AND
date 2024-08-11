package com.example.sep4_and.network.api;

import com.example.sep4_and.model.User;
import com.example.sep4_and.network.requests.LoginRequest;
import com.example.sep4_and.network.requests.RegisterRequest;
import com.example.sep4_and.network.responses.LoginResponse;
import com.example.sep4_and.network.responses.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @POST("user")
    Call<User> register(@Body RegisterRequest registerRequest);

    @POST("user/login")
    Call<User> login(@Body LoginRequest loginRequest);

    @GET("user/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("users")
    Call<List<User>> getAllUsers();

    @PUT("user/{id}")
    Call<Void> updateUser(@Path("id") int id, @Query("firstName") String firstName, @Query("lastName") String lastName, @Query("email") String email, @Query("password") String password);
}
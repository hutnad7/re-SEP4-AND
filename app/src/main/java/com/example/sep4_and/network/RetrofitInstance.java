package com.example.sep4_and.network;


import com.example.sep4_and.BuildConfig;
import com.example.sep4_and.network.interceptors.AuthInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static AuthInterceptor authInterceptor = new AuthInterceptor();

    public static Retrofit getClient(String baseUrl) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(authInterceptor)
                .build();

        if (retrofit == null) {
             retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                     .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
    public static void setAuthToken(String token) {
        authInterceptor.setToken(token);
    }
}
package com.example.sep4_and.network;


import com.example.sep4_and.BuildConfig;
import com.example.sep4_and.network.interceptors.AuthInterceptor;
import com.example.sep4_and.utils.LiveDataCallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static AuthInterceptor authInterceptor = new AuthInterceptor();
    //Replace with baseUrl or replace baseUrl with the UI in the config
    private static final String MockUrL = "https://2df19db1-b773-4c0c-a100-e4309c0cb80c.mock.pstmn.io";

    public static Retrofit getClient(String baseUrl) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(authInterceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .build();
        }
        return retrofit;
    }

    public static void setAuthToken(String token) {
        authInterceptor.setToken(token);
    }
}
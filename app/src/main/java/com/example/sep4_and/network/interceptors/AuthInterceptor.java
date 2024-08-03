package com.example.sep4_and.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();

        if (token != null && !original.url().encodedPath().contains("login") && !original.url().encodedPath().contains("register")) {
            requestBuilder.header("Authorization", "Bearer " + token);
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
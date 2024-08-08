package com.example.sep4_and.network.api;

import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.Notification;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationApi {
    @POST("notifications")
    LiveData<Notification> createNotification(@Body Notification notification);

    @GET("notifications/{userId}")
    LiveData<List<Notification>> getNotifications(@Path("userId") int userId);
}
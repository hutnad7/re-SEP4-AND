package com.example.sep4_and.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.NotificationDao;
import com.example.sep4_and.model.Notification;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.NotificationApi;
import com.example.sep4_and.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationRepository {
    private NotificationDao notificationDao;
    private NotificationApi notificationApi;
    private ExecutorService executorService;

    public NotificationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        notificationDao = db.notificationDao();
        notificationApi = RetrofitInstance.getClient("").create(NotificationApi.class);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Notification notification) {
        executorService.execute(() -> {
            if (Config.isUseApi()) {
                notificationApi.createNotification(notification).observeForever(apiResult -> {
                    if (Config.isEchoToLocalDatabase() && apiResult != null) {
                        notificationDao.insert(notification); // Echo to local DB
                    }
                });
            } else {
                notificationDao.insert(notification);
            }
        });
    }

    public LiveData<List<Notification>> getNotificationsForUser(int userId) {
        if (Config.isUseApi()) {
            MutableLiveData<List<Notification>> result = new MutableLiveData<>();
            notificationApi.getNotifications(userId).observeForever(notifications -> {
                result.postValue(notifications);
                if (Config.isEchoToLocalDatabase()) {
                     // No need to echo for view only data
                }
            });
            return result;
        } else {
            return notificationDao.getNotificationsForUser(userId);
        }
    }
}
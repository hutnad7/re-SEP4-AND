package com.example.sep4_and.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.NotificationDao;
import com.example.sep4_and.model.Notification;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationRepository {
    private NotificationDao notificationDao;
    private ExecutorService executorService;

    public NotificationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        notificationDao = db.notificationDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Notification notification) {
        executorService.execute(() -> notificationDao.insert(notification));
    }

    public LiveData<List<Notification>> getNotificationsForUser(int userId) {
        return notificationDao.getNotificationsForUser(userId);
    }
}
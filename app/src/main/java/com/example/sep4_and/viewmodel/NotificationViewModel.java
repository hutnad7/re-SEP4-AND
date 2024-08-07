package com.example.sep4_and.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.Notification;
import com.example.sep4_and.repository.NotificationRepository;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private NotificationRepository repository;

    public NotificationViewModel(Application application) {
        super(application);
        repository = new NotificationRepository(application);
    }

    public void insert(Notification notification) {
        repository.insert(notification);
    }

    public LiveData<List<Notification>> getNotificationsForUser(int userId) {
        return repository.getNotificationsForUser(userId);
    }
}

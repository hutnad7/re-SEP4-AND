package com.example.sep4_and.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sep4_and.model.Notification;

import java.util.List;

@Dao
public interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notification notification);

    @Query("SELECT * FROM notifications WHERE userId = :userId")
    LiveData<List<Notification>> getNotificationsForUser(int userId);
}

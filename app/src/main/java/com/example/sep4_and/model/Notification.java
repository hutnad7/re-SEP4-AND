package com.example.sep4_and.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.sep4_and.convertors.DateConverter;

import java.util.Calendar;
import java.util.Date;

//Bad name "Notification", can be confused with android key word
@Entity(
        tableName = "notifications",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String message;

    @TypeConverters(DateConverter.class)
    private Date time;

    private boolean isRecurrent;
    private int userId;

    public Notification(String message, Date time, boolean isRecurrent, int userId) {
        this.message = message;
        this.time = time;
        this.isRecurrent = isRecurrent;
        this.userId = userId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isRecurrent() {
        return isRecurrent;
    }

    public void setRecurrent(boolean recurrent) {
        isRecurrent = recurrent;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public void updateRecurrentNotification() {
        if (isRecurrent) {
            Date currentDate = new Date();
            if (currentDate.after(time)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                calendar.add(Calendar.WEEK_OF_YEAR, 1); // Add 1 week to the set date
                time = calendar.getTime();
            }
        }
    }
}
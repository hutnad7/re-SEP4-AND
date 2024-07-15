package com.example.sep4_and.dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

import com.example.sep4_and.convertors.DateConverter;
import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.convertors.MeasurementTypeConverter;
import com.example.sep4_and.model.User;


@Database(entities = {User.class, GreenHouse.class, GreenHouseUserCrossRef.class}, version = 10, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract GreenHouseDao greenHouseDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration() // This clears the database on version change
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

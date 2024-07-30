package com.example.sep4_and.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sep4_and.model.Measurement;

import java.util.Date;
import java.util.List;

@Dao
public interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Measurement measurement);

    @Query("SELECT * FROM measurements WHERE greenHouseId = :greenHouseId")
    LiveData<List<Measurement>> getMeasurementsForGreenHouse(int greenHouseId);

    @Query("SELECT * FROM measurements WHERE timestamp BETWEEN :startDate AND :endDate")
    LiveData<List<Measurement>> getMeasurementsForDateRange(Date startDate, Date endDate);
}

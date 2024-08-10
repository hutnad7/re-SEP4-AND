package com.example.sep4_and.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.model.MeasurementType;

import java.util.List;


@Dao
public interface ThresholdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Threshold threshold);

    @Update
    void update(Threshold threshold);

    @Query("SELECT * FROM thresholds WHERE greenHouseId = :greenHouseId")
    LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId);

    @Query("SELECT * FROM thresholds WHERE greenHouseId = :greenHouseId AND type = :type LIMIT 1")
    Threshold getThresholdForGreenHouseByType(int greenHouseId, MeasurementType type);

    @Delete
    void delete(Threshold threshold);
}

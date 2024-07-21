package com.example.sep4_and.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sep4_and.model.Threshold;

import java.util.List;


@Dao
public interface ThresholdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Threshold threshold);

    @Query("SELECT * FROM thresholds WHERE greenHouseId = :greenHouseId")
    LiveData<List<Threshold>> getThresholdsForGreenHouse(int greenHouseId);
}

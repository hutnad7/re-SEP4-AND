package com.example.sep4_and.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sep4_and.model.Measurement;
import com.example.sep4_and.model.MeasurementType;

import java.util.List;

@Dao
public interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Measurement measurement);

    @Query("SELECT * FROM measurements WHERE greenHouseId = :greenHouseId")
    LiveData<List<Measurement>> getMeasurementsForGreenHouse(int greenHouseId);

    @Query("SELECT * FROM measurements WHERE greenHouseId = :greenHouseId AND type = :type ORDER BY timestamp DESC LIMIT 1")
    LiveData<Measurement> getLatestMeasurementForType(int greenHouseId, MeasurementType type);
}
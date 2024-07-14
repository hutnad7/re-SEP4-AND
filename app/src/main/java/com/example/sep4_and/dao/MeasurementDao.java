package com.example.sep4_and.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.sep4_and.model.Measurement;

@Dao
public interface MeasurementDao {
    @Insert
    void insert(Measurement measurement);

}

package com.example.sep4_and.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import com.example.sep4_and.model.GreenHouse;

import java.util.List;

@Dao
public interface GreenHouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(GreenHouse greenHouse);

    @Delete
    void delete(GreenHouse greenHouse);

    @Query("SELECT * FROM greenhouses WHERE userId = :userId")
    LiveData<List<GreenHouse>> getGreenHousesByUserId(int userId);

    @Query("SELECT * FROM greenhouses WHERE id = :greenHouseId")
    LiveData<GreenHouse> getGreenHouseById(int greenHouseId);
}
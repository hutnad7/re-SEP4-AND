package com.example.sep4_and.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;
import com.example.sep4_and.model.DbCrossReference.GreenHouseWithUsers;
import com.example.sep4_and.model.GreenHouse;

import java.util.List;

@Dao
public interface GreenHouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GreenHouse greenHouse);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGreenHouseUserCrossRef(GreenHouseUserCrossRef crossRef);

    @Transaction
    @Query("SELECT * FROM greenhouses")
    LiveData<List<GreenHouseWithUsers>> getGreenHousesWithUsers();
}
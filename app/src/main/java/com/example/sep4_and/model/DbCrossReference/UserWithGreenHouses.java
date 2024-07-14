package com.example.sep4_and.model.DbCrossReference;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.User;

import java.util.List;

public class UserWithGreenHouses {
    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(GreenHouseUserCrossRef.class)
    )
    public List<GreenHouse> greenHouses;
}
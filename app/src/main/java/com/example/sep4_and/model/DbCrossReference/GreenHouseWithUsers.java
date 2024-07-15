package com.example.sep4_and.model.DbCrossReference;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.User;

import java.util.List;

public class GreenHouseWithUsers {
    @Embedded public GreenHouse greenHouse;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = GreenHouseUserCrossRef.class,
                    parentColumn = "greenHouseId",
                    entityColumn = "userId"
            )
    )
    public List<User> users;
}

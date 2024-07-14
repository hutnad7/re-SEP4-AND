package com.example.sep4_and.model.DbCrossReference;

import androidx.room.Entity;

@Entity(primaryKeys = {"greenHouseId", "userId"})
public class GreenHouseUserCrossRef {
    public int greenHouseId;
    public int userId;
}

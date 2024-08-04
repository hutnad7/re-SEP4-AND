package com.example.sep4_and.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import java.util.List;

@Entity(
        tableName = "greenhouses",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class GreenHouse {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;
    private int userId; // Foreign key to User

    @Ignore
    @Relation(parentColumn = "id", entityColumn = "greenHouseId")
    private List<Threshold> thresholds;

    // Constructor, getters, and setters
    public GreenHouse(String name, String location, int userId) {
        this.name = name;
        this.location = location;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Threshold> getThresholds() {
        return thresholds;
    }

    public void setThresholds(List<Threshold> thresholds) {
        this.thresholds = thresholds;
    }
}
package com.example.sep4_and.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.sep4_and.convertors.MeasurementTypeConverter;

@Entity(
        tableName = "thresholds",
        foreignKeys = @ForeignKey(
                entity = GreenHouse.class,
                parentColumns = "id",
                childColumns = "greenHouseId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Threshold {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(MeasurementTypeConverter.class)
    private MeasurementType type;

    private float minValue;
    private float maxValue;
    private int greenHouseId;

    public Threshold(MeasurementType type, float minValue, float maxValue, int greenHouseId) {
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.greenHouseId = greenHouseId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MeasurementType getType() {
        return type;
    }

    public void setType(MeasurementType type) {
        this.type = type;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public int getGreenHouseId() {
        return greenHouseId;
    }

    public void setGreenHouseId(int greenHouseId) {
        this.greenHouseId = greenHouseId;
    }
}

package com.example.sep4_and.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.sep4_and.convertors.DateConverter;
import com.example.sep4_and.convertors.MeasurementTypeConverter;

import java.util.Date;

@Entity(
        tableName = "measurements",
        foreignKeys = @ForeignKey(
                entity = GreenHouse.class,
                parentColumns = "id",
                childColumns = "greenHouseId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Measurement {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(MeasurementTypeConverter.class)
    private MeasurementType type;

    private float value;

    @TypeConverters(DateConverter.class)
    private Date timestamp;

    private int greenHouseId;

    public Measurement(MeasurementType type, float value, Date timestamp, int greenHouseId) {
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getGreenHouseId() {
        return greenHouseId;
    }

    public void setGreenHouseId(int greenHouseId) {
        this.greenHouseId = greenHouseId;
    }
}

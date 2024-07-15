package com.example.sep4_and.convertors;

import androidx.room.TypeConverter;

import com.example.sep4_and.model.MeasurementType;

public class MeasurementTypeConverter {
    @TypeConverter
    public static MeasurementType fromString(String value) {
        return value == null ? null : MeasurementType.valueOf(value);
    }

    @TypeConverter
    public static String measurementTypeToString(MeasurementType type) {
        return type == null ? null : type.name();
    }
}

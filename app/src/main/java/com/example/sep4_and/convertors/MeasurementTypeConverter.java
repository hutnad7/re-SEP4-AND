package com.example.sep4_and.convertors;

import androidx.room.TypeConverter;

import com.example.sep4_and.model.MeasurementType;

public class MeasurementTypeConverter {
    @TypeConverter
    public static MeasurementType toMeasurementType(String value) {
        return MeasurementType.valueOf(value);
    }

    @TypeConverter
    public static String fromMeasurementType(MeasurementType type) {
        return type.name();
    }
}

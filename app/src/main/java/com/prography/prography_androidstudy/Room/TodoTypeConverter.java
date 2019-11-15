package com.prography.prography_androidstudy.Room;

import androidx.room.TypeConverter;

import java.util.Date;

public class TodoTypeConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

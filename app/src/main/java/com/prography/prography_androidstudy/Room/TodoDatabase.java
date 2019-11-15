package com.prography.prography_androidstudy.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Todo.class}, version = 1)
@TypeConverters({TodoTypeConverter.class})
public abstract class TodoDatabase extends RoomDatabase {

    private static volatile TodoDatabase INSTANCE;

    static TodoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, "notes")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    abstract public TodosDao notesDao();
}

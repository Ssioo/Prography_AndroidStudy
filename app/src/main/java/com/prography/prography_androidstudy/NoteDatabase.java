package com.prography.prography_androidstudy;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
abstract class NoteDatabase extends RoomDatabase {

    private static volatile NoteDatabase INSTANCE;

    static NoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "notes")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    abstract public NotesDao notesDao();
}

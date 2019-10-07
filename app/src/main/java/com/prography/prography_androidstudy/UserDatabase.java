package com.prography.prography_androidstudy;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class}, version = 1)
abstract class UserDatabase extends RoomDatabase {

    abstract public UserDao userDao();

    private static volatile UserDatabase INSTANCE;

    static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    // Create Database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "users")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

package com.srinjoy.jobtracker.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {JobApplication.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract JobDao jobDao();
}
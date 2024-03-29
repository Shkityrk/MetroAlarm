package com.example.samsungschoolproject.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.samsungschoolproject.model.Station;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Station.class}, version = 1, exportSchema = false)
public abstract class StationDatabase extends RoomDatabase {
    public abstract StationDAO stationDAO();

    private static volatile StationDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static StationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StationDatabase.class, "stations")
                            //.createFromAsset("database/stations.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

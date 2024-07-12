package com.example.samsungschoolproject.database.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {com.example.samsungschoolproject.model.Station.class}, version = 2, exportSchema = false)
public abstract class Station extends RoomDatabase {
    public abstract com.example.samsungschoolproject.database.DAO.Station stationDAO();


    private static volatile Station INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Station getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Station.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Station.class, "station_neighbors")
                            .createFromAsset("database/stations.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

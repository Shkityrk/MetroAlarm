package com.example.samsungschoolproject.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface Station {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(com.example.samsungschoolproject.model.Station station);

    @Query("SELECT * FROM station_neighbors")
    LiveData<List<com.example.samsungschoolproject.model.Station>> getAllStations();

    @Query("UPDATE station_neighbors SET alarm = :alarm, is_favourite= :alarm WHERE id_station = :id")
    void updateAlarm(int id, String alarm);

    @Query("SELECT * FROM station_neighbors WHERE is_favourite = 'true'")
    LiveData<List<com.example.samsungschoolproject.model.Station>> getFavouriteStations();

    @Query("SELECT * FROM station_neighbors WHERE alarm = 'true' AND is_favourite = 'true'")
    LiveData<List<com.example.samsungschoolproject.model.Station>> getAlarmStations();

    @Update
    void update(com.example.samsungschoolproject.model.Station station);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<com.example.samsungschoolproject.model.Station> stations);

    @Query("DELETE FROM station_neighbors")
    void deleteAll();

    @Update
    public void updateStations(List<com.example.samsungschoolproject.model.Station> stations); // Метод для обновления списка станций

    @Update
    void updateStation(com.example.samsungschoolproject.model.Station station);

    @Delete
    void deleteStation(com.example.samsungschoolproject.model.Station station);

    @Query("SELECT * FROM station_neighbors")
    List<com.example.samsungschoolproject.model.Station> getAllStationsSync();
}
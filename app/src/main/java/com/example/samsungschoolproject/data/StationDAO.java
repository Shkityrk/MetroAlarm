package com.example.samsungschoolproject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.samsungschoolproject.model.Station;

import java.util.List;
@Dao
public interface StationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Station station);

    @Query("SELECT * FROM station_neighbors")
    LiveData<List<Station>> getAllStations();

    @Query("UPDATE station_neighbors SET alarm = :alarm, is_favourite= :alarm WHERE id_station = :id")
    void updateAlarm(int id, String alarm);

    @Query("SELECT * FROM station_neighbors WHERE is_favourite = 'true'")
    LiveData<List<Station>> getFavouriteStations();

    @Query("SELECT * FROM station_neighbors WHERE alarm = 'true' AND is_favourite = 'true'")
    LiveData<List<Station>> getAlarmStations();

    @Update
    void update(Station station);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Station> stations);

    @Query("DELETE FROM station_neighbors")
    void deleteAll();

    @Update
    public void updateStations(List<Station> stations); // Метод для обновления списка станций

    @Update
    void updateStation(Station station);

    @Delete
    void deleteStation(Station station);

    @Query("SELECT * FROM station_neighbors")
    List<Station> getAllStationsSync();
}
package com.example.samsungschoolproject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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

    @Query("UPDATE station_neighbors SET alarm = :alarm WHERE id_station = :id")
    void updateAlarm(int id, String alarm);

//    @Update
//    void update(Station station);

    @Update
    public void updateStations(List<Station> stations); // Метод для обновления списка станций
}

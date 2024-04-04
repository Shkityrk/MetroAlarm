package com.example.samsungschoolproject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.samsungschoolproject.model.Station;

import java.util.List;
@Dao
public interface StationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Station station);

    @Query("SELECT * FROM station_neighbors")
    LiveData<List<Station>> getAllStations();


}

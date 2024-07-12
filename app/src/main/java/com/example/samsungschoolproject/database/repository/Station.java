package com.example.samsungschoolproject.database.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.samsungschoolproject.model.StationData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Station {
    private com.example.samsungschoolproject.database.DAO.Station mStation;
    private LiveData<List<com.example.samsungschoolproject.model.Station>> mAllStations;
    private List<com.example.samsungschoolproject.model.Station> allStations;
    private com.example.samsungschoolproject.database.DAO.Station station;

    public Station(Application application) {
        com.example.samsungschoolproject.database.database.Station db = com.example.samsungschoolproject.database.database.Station.getDatabase(application);
        mStation = db.stationDAO();
        mAllStations = mStation.getAllStations();
    }

    public List<com.example.samsungschoolproject.model.Station> getAllStationsList() {
        return allStations;
    }


    public LiveData<List<com.example.samsungschoolproject.model.Station>> getAllStations() {
        return mAllStations;
    }

    public void insert(com.example.samsungschoolproject.model.Station station) {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {
            mStation.insert(station);

        });
    }

    public void update(com.example.samsungschoolproject.model.Station station) {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {
            mStation.update(station);
        });
    }

    public void updateStations(List<com.example.samsungschoolproject.model.Station> stations) {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {
            mStation.updateStations(stations);
        });
    }

    public LiveData<List<com.example.samsungschoolproject.model.Station>> getFavouriteStations() {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {
            mStation.getFavouriteStations();
        });
        return mStation.getFavouriteStations();
    }

    public LiveData<List<com.example.samsungschoolproject.model.Station>> getAlarmStations() {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {
            mStation.getAlarmStations();
        });
        return mStation.getAlarmStations();
    }

    public void updateStation(com.example.samsungschoolproject.model.Station station) {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {
            mStation.updateStation(station);
        });
    }

    public void deleteStation(com.example.samsungschoolproject.model.Station station) {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {
            mStation.deleteStation(station);
        });
    }

    public void deleteAndInsertAll(List<com.example.samsungschoolproject.model.Station> stations, Context context) {
        com.example.samsungschoolproject.database.database.Station.databaseWriteExecutor.execute(() -> {

            Map<Integer, StationData> stationDataMap = new HashMap<>();

            com.example.samsungschoolproject.database.database.Station stationDatabase = com.example.samsungschoolproject.database.database.Station.getDatabase(context);
            station = stationDatabase.stationDAO();
            stationDataMap.clear();

            // Получим список всех станций из базы данных
            List<com.example.samsungschoolproject.model.Station> allStations = station.getAllStationsSync();


            // Заполним словарь данными из станций
            for (com.example.samsungschoolproject.model.Station station : allStations) {
                StationData data = new StationData(station.getIs_favourite(), station.getAlarm());
                Log.d("LALALA", station.getName() + " " + station.getIs_favourite() + " " + station.getAlarm());
                Log.d("LALALA-data", data.getIsFavourite() + " " + data.getAlarm());
                Log.d("LALALA", " ");
                stationDataMap.put(station.getId(), data);
            }
            Log.d("Server", stationDataMap.toString());


            mStation.deleteAll(); // Удалить все записи

            for (com.example.samsungschoolproject.model.Station station : stations) {
                StationData data = stationDataMap.get(station.getId());
                if (data != null) {
                    Log.d("server", data.getIsFavourite() +" "+ data.toString());
                    if(Objects.equals(data.getIsFavourite(), "false")){
                        station.setFavourite(false);
                    }else{
                        station.setFavourite(true);
                    }

                    if(Objects.equals(data.getAlarm(), "false")){
                        station.setAlarm(false);
                    }else{
                        station.setAlarm(true);
                    }

                    Log.d("SERVER", station.getName() +" " + station.getIs_favourite());



                }
            }


            mStation.insertAll(stations); // Вставить новые записи

        });
    }


}

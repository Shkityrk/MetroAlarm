package com.example.samsungschoolproject.data;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.samsungschoolproject.model.Station;
import com.example.samsungschoolproject.model.StationData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StationRepository {
    private StationDAO mStationDAO;
    private LiveData<List<Station>> mAllStations;
    private List<Station> allStations;
    private StationDAO stationDAO;

    public StationRepository(Application application) {
        StationDatabase db = StationDatabase.getDatabase(application);
        mStationDAO = db.stationDAO();
        mAllStations = mStationDAO.getAllStations();
    }

    public List<Station> getAllStationsList() {
        return allStations;
    }


    public LiveData<List<Station>> getAllStations() {
        return mAllStations;
    }

    public void insert(Station station) {
        StationDatabase.databaseWriteExecutor.execute(() -> {
            mStationDAO.insert(station);

        });
    }

//    public void update(Station station) {
//        StationDatabase.databaseWriteExecutor.execute(() -> {
//            mStationDAO.update(station);
//        });
//    }

    public void updateStations(List<Station> stations) {
        StationDatabase.databaseWriteExecutor.execute(() -> {
            mStationDAO.updateStations(stations);
        });
    }

    public LiveData<List<Station>> getFavouriteStations() {
        StationDatabase.databaseWriteExecutor.execute(() -> {
            mStationDAO.getFavouriteStations();
        });
        return mStationDAO.getFavouriteStations();
    }

    public LiveData<List<Station>> getAlarmStations() {
        StationDatabase.databaseWriteExecutor.execute(() -> {
            mStationDAO.getAlarmStations();
        });
        return mStationDAO.getAlarmStations();
    }

    public void updateStation(Station station) {
        StationDatabase.databaseWriteExecutor.execute(() -> {
            mStationDAO.updateStation(station);
        });
    }

    public void deleteStation(Station station) {
        StationDatabase.databaseWriteExecutor.execute(() -> {
            mStationDAO.deleteStation(station);
        });
    }

    public void deleteAndInsertAll(List<Station> stations, Context context) {
        StationDatabase.databaseWriteExecutor.execute(() -> {

            Map<Integer, StationData> stationDataMap = new HashMap<>();

            StationDatabase stationDatabase = StationDatabase.getDatabase(context);
            stationDAO = stationDatabase.stationDAO();
            stationDataMap.clear();

            // Получим список всех станций из базы данных
            List<Station> allStations = stationDAO.getAllStationsSync();


            // Заполним словарь данными из станций
            for (Station station : allStations) {
                StationData data = new StationData(station.getIs_favourite(), station.getAlarm());
                Log.d("LALALA", station.getName() + " " + station.getIs_favourite() + " " + station.getAlarm());
                Log.d("LALALA-data", data.getIsFavourite() + " " + data.getAlarm());
                Log.d("LALALA", " ");
                stationDataMap.put(station.getId(), data);
            }
            Log.d("Server", stationDataMap.toString());


            mStationDAO.deleteAll(); // Удалить все записи

            for (Station station : stations) {
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


            mStationDAO.insertAll(stations); // Вставить новые записи

        });
    }


}

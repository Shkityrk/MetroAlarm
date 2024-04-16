package com.example.samsungschoolproject.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.samsungschoolproject.model.Station;

import java.util.List;

public class StationRepository {


        private StationDAO mStationDAO;
        private LiveData<List<Station>> mAllStations;
        private List<Station> allStations;

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

//        public void update(Station station) {
//            StationDatabase.databaseWriteExecutor.execute(() -> {
//                mStationDAO.update(station);
//            });
//        }

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
}

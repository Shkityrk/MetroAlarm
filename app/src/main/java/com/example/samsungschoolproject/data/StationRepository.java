package com.example.samsungschoolproject.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.samsungschoolproject.model.Station;

import java.util.List;

public class StationRepository {


        private StationDAO mStationDAO;
        private LiveData<List<Station>> mAllStations;

        public StationRepository(Application application) {
            StationDatabase db = StationDatabase.getDatabase(application);
            mStationDAO = db.stationDAO();
            mAllStations = mStationDAO.getAllStations();
        }


        public LiveData<List<Station>> getAllStations() {
            return mAllStations;
        }

        public void insert(Station station) {
            StationDatabase.databaseWriteExecutor.execute(() -> {
                mStationDAO.insert(station);
            });
        }
}

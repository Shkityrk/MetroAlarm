package com.example.samsungschoolproject.fragment.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.samsungschoolproject.data.StationRepository;
import com.example.samsungschoolproject.model.Station;

import java.util.List;

public class StationsViewModel extends AndroidViewModel {

    private StationRepository mRepository;

    private final LiveData<List<Station>> mAllStation;

    public StationsViewModel (Application application) {
        super(application);
        mRepository = new StationRepository(application);
        mAllStation = mRepository.getAllStations();
    }

    public LiveData<List<Station>> getAllWords() {
        return mAllStation;
    }

    public void insert(Station station) {
        mRepository.insert(station);
    }
}
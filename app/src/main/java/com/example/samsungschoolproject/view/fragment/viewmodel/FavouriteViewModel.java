package com.example.samsungschoolproject.view.fragment.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.samsungschoolproject.database.repository.Station;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    private Station mRepository;

    private final LiveData<List<com.example.samsungschoolproject.model.Station>> mAllStation;

    public FavouriteViewModel (Application application) {
        super(application);
        mRepository = new Station(application);
        mAllStation = mRepository.getFavouriteStations();//-------------------
    }

    public LiveData<List<com.example.samsungschoolproject.model.Station>> getAllWords() {
        return mAllStation;
    }

    public void insert(com.example.samsungschoolproject.model.Station station) {
        mRepository.insert(station);
    }
//    public void update(Station station) { mRepository.update(station); }

    public void updateStations(List<com.example.samsungschoolproject.model.Station> stations) { mRepository.updateStations(stations); }

}
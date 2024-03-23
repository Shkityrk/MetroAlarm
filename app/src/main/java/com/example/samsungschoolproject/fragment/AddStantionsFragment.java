package com.example.samsungschoolproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.adapter.StationAdapter;
import com.example.samsungschoolproject.model.Station;
import com.example.samsungschoolproject.view_adapter.StationListAdapter;

public class AddStantionsFragment extends Fragment {
    private StationListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_stantions, container, false);
        Button back_to_stations = (Button) view.findViewById(R.id.back_to_stations);
        Button done = (Button) view.findViewById(R.id.done);

        RecyclerView rv_add_station = view.findViewById(R.id.rv_add_stantion);
        rv_add_station.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false
        ));
        StationAdapter stationAdapter = new StationAdapter();

        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));




        rv_add_station.setAdapter(stationAdapter);


        back_to_stations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StationsListFragment stationsListFragment = new StationsListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, stationsListFragment);

                transaction.commit();
            }
        });














        return view;
    }
}
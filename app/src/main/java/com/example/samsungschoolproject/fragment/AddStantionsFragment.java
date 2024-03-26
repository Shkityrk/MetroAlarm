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
import com.example.samsungschoolproject.adapter.AllStationAdapter;
import com.example.samsungschoolproject.adapter.FavouriteStationAdapter;
import com.example.samsungschoolproject.model.Station;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddStantionsFragment extends Fragment {


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
        AllStationAdapter allStationAdapter = new AllStationAdapter();
//        stationAdapter.Add(new Station("Тропарево", "Сокольническая линия"));
//        stationAdapter.Add(new Station("Окружная", "МЦК"));
//----------------------------------------------
        List<Station> stations = new ArrayList<>();

        // Путь к вашему файлу stations.csv
        String csvFile = "stations.csv";
        String line;
        String csvSplitBy = ",";

        try  {
            InputStream inputStream = getResources().getAssets().open("stations.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                // Разделить строку CSV по запятым
                String[] data = line.split(csvSplitBy);
                // Удалить кавычки из значений
                String name = data[1];
                String lineName = data[0];
                // Создать объект Station и добавить его в список
                stations.add(new Station(name, lineName));
            }
            br.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Теперь у вас есть список станций. Можете использовать его по вашему усмотрению.
        // Например, добавить их в ваш адаптер

        for (Station station : stations) {
            allStationAdapter.Add(station);
        }
//----------------------------------------------


        rv_add_station.setAdapter(allStationAdapter);


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

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuFragment mainMenuFragment = new MainMenuFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, mainMenuFragment);

                transaction.commit();
            }
        });
















        return view;
    }
}
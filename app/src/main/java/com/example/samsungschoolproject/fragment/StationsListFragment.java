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

public class StationsListFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stantions_list, container, false);
        Button back_to_main_menu_button= (Button) view.findViewById(R.id.back_to_main_menu_button);

        Button create_new_alarm = (Button) view.findViewById(R.id.create_new_alarm);
        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false
        ));

        StationAdapter stationAdapter = new StationAdapter();
        stationAdapter.Add(new Station("Юго-западная", "Сокольническая линия"));
        stationAdapter.Add(new Station("Коптево", "МЦК"));
        stationAdapter.Add(new Station("Проспект Вернадского", "МЦК-Сокольническая линия"));
        stationAdapter.Add(new Station("Киевская", "Кольцевая линия-Арбатско-покровская линия-Филевская линия"));


        rv.setAdapter(stationAdapter);





        back_to_main_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuFragment mainMenuFragment = new MainMenuFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, mainMenuFragment);

                transaction.commit();
            }
        });

        create_new_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStantionsFragment addStantionsFragment = new AddStantionsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, addStantionsFragment);

                transaction.commit();
            }
        });


        return view;

    }




}
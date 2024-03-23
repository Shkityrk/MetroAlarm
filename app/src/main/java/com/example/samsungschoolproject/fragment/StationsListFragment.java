package com.example.samsungschoolproject.fragment;

import android.content.Intent;
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

import java.util.ArrayList;

public class StationsListFragment extends Fragment {

    private StationListAdapter adapter;
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
        stationAdapter.Add(new Station("Station 1", "Line 1", "Color 1"));
        stationAdapter.Add(new Station("Station 2", "Line 2", "Color 2"));
        stationAdapter.Add(new Station("Station 3", "Line 3", "Color 3"));
        stationAdapter.Add(new Station("Station 4", "Line 4", "Color 4"));




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
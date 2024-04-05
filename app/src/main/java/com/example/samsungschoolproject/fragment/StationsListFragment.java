package com.example.samsungschoolproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.adapter.FavouriteStationAdapter;
import com.example.samsungschoolproject.fragment.viewmodel.FavouriteViewModel;
import com.example.samsungschoolproject.model.Station;

import java.util.List;

public class StationsListFragment extends Fragment {
    private FavouriteViewModel mStationViewModel;
    public FavouriteViewModel getStationViewModel() {
        return mStationViewModel;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStationViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
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

        FavouriteStationAdapter favouriteStationAdapter = new FavouriteStationAdapter(new FavouriteStationAdapter.StationDiff());

        //мусор, нужен был для теста :)
//        favouriteStationAdapter.Add(new Station(1,"Юго-западная", "Сокольническая линия","null","null","null","null","null","null","null","null"));
//        favouriteStationAdapter.Add(new Station(2,"Коптево", "МЦК","null","null" ,"null","null","null","null","null","null"));
//        favouriteStationAdapter.Add(new Station(3,"Проспект Вернадского", "МЦК-Сокольническая линия","null","null","null","null","null","null","null","null"));
//        favouriteStationAdapter.Add(new Station(4,"Киевская", "Кольцевая линия-Арбатско-покровская линия-Филевская линия","null","null" ,"null","null","null","null","null","null"));

        mStationViewModel.getAllWords().observe(getViewLifecycleOwner(), stationsList -> {
            favouriteStationAdapter.submitList(stationsList);

            for (int i = 0; i < stationsList.size(); i++) {
                Station station = stationsList.get(i);
                boolean favourState = station.getBoolAlarm();
                favouriteStationAdapter.setSwitchState(favourState, i);
            }
        });
//        favouriteStationAdapter.setSwitchChangeListener(new AllStationAdapter.OnSwitchChangeListener() {
//        @Override
//        public void onSwitchChanged(int position, boolean isChecked) {
//            // Обновите значение в базе данных при изменении переключателя
//            Station station = favouriteStationAdapter.getCurrentList().get(position);
//            station.setAlarm(isChecked);
//
//        }
//    });

        rv.setAdapter(favouriteStationAdapter);





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
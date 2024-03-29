package com.example.samsungschoolproject.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.StationLoaderCSV;
import com.example.samsungschoolproject.StationLoaderDB;
import com.example.samsungschoolproject.databinding.AddStationItemBinding;
import com.example.samsungschoolproject.model.Station;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class AllStationAdapter extends RecyclerView.Adapter<AllStationAdapter.AllStationViewHolder>{
    private Context mContext;
    private List<Station> stations = new ArrayList<>();
    private static final String DATABASE_NAME = "stations.db";
    private String url;

//    public AllStationAdapter(Context context) {
//        mContext = context;
//        AssetManager assetManager = mContext.getAssets();
//
//        try {
//            InputStream inputStream = assetManager.open(DATABASE_NAME);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String databasePath = mContext.getDatabasePath(DATABASE_NAME).getPath();
//        url = "jdbc:sqlite:" + databasePath;
//    }

    @NonNull
    @Override
    public AllStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.add_station_item,
                parent,
                false
        );
        return new AllStationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStationViewHolder holder, int position) {
        holder.bind(stations.get(position));
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class AllStationViewHolder extends RecyclerView.ViewHolder{
        private  AddStationItemBinding addStationItemBinding;

        public AllStationViewHolder(@NonNull View itemView) {
            super(itemView);
            addStationItemBinding = AddStationItemBinding.bind(itemView);
        }
        public void bind(Station station){
            addStationItemBinding.addStation.setText(station.getName());
            addStationItemBinding.lineAdd.setText(station.getLine());

        }
    }
    public void Add(Station station){
        stations.add(station);
        notifyDataSetChanged();
    }
//Database

    public static List<Station> GetStationsDatabase(String database_name) {
        StationLoaderDB dbHandler = new StationLoaderDB(database_name);
        List<Station> stations = dbHandler.getStations();

        if (stations != null) {
            for (Station station : stations) {
                System.out.println(station.getName() + ", " + station.getLine() + ", " + station.getLongitude() + ", " + station.getLatitude());
            }
        }
        return stations;

    }

//CSV
//    public static List<Station> getStations(String path) {
//        List<Station> stations = new ArrayList<>();
//        String line;
//        String csvSplitBy = ",";
//
//        try (InputStream inputStream = StationLoaderCSV.class.getResourceAsStream(path);
//             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
//
//            if (inputStream == null) {
//                throw new IOException("Resource not found: " + path);
//            }
//
//            while ((line = br.readLine()) != null) {
//                // Разделить строку CSV по запятым
//                String[] data = line.split(csvSplitBy);
//                // Удалить кавычки из значений
//                String name = data[1];
//                String lineName = data[0];
//                // Создать объект Station и добавить его в список
//                stations.add(new Station(name, lineName,"null","null"  ));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return stations;
//    }



}

package com.example.samsungschoolproject;

import android.content.res.AssetManager;

import com.example.samsungschoolproject.model.Station;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StationLoaderCSV {
    public static List<Station> loadStations_csv(AssetManager assetManager) {
        List<Station> stations = new ArrayList<>();

        // Путь к вашему файлу stations.csv
        String csvFile = "stations.csv";
        String line;
        String csvSplitBy = ",";

        try {
            InputStream inputStream = assetManager.open(csvFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                // Разделить строку CSV по запятым
                String[] data = line.split(csvSplitBy);
                // Удалить кавычки из значений
                String name = data[1];
                String lineName = data[0];
                // Создать объект Station и добавить его в список
                stations.add(new Station(name, lineName, "null", "null"));
            }
            br.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stations;
    }


    
}




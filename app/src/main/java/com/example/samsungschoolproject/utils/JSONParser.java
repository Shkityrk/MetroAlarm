package com.example.samsungschoolproject.utils;

import com.example.samsungschoolproject.model.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static List<Station> stationsParseJSON(String jsonString) {
        List<Station> stationList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int stationId = jsonObject.getInt("id_station");
                String name = jsonObject.getString("name");
                String line = jsonObject.getString("line");
                String latitude = jsonObject.getString("latitude");
                String longitude = jsonObject.getString("longitude");
                String widthNeighbour1 = jsonObject.optString("width_neighbour1", null);
                String longitudeNeighbour1 = jsonObject.optString("longitude_neighbour1", null);
                String widthNeighbour2 = jsonObject.optString("width_neighbour2", null);
                String longitudeNeighbour2 = jsonObject.optString("longitude_neighbour2", null);
                String isFavourite = jsonObject.getString("is_favourite");
                String alarm = jsonObject.getString("alarm");

                Station station = new Station(name, line, latitude, longitude,
                        widthNeighbour1, longitudeNeighbour1, widthNeighbour2,
                        longitudeNeighbour2, isFavourite, alarm);
                station.setId(stationId);
                stationList.add(station);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stationList;
    }
}

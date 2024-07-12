package com.example.samsungschoolproject.utils;

import com.example.samsungschoolproject.model.Station;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static List<Station> stationsParseJSON(String jsonString) {
        List<Station> stationList = new ArrayList<>();
        Gson gson = new Gson();

        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            Station station = gson.fromJson(jsonElement, Station.class);
            stationList.add(station);
        }

        return stationList;
    }
}

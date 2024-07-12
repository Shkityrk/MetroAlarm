package com.example.samsungschoolproject.utils;

import com.example.samsungschoolproject.model.Station;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    /**
     * Метод для парсинга JSON-строки в список объектов Station.
     *
     * @param jsonString JSON-строка для парсинга
     * @return Список объектов Station, полученных из JSON-строки
     */
    public static List<Station> stationsParseJSON(String jsonString) {
        List<Station> stationList = new ArrayList<>();
        Gson gson = new Gson();

        // Парсинг JSON-строки в JsonArray
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Проход по каждому элементу JsonArray и десериализация в объект Station
        for (JsonElement jsonElement : jsonArray) {
            Station station = gson.fromJson(jsonElement, Station.class);
            stationList.add(station);
        }

        return stationList;
    }
}

package com.example.samsungschoolproject;
import com.example.samsungschoolproject.model.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationLoaderDB {
    private Connection connection;

    public  StationLoaderDB(String databaseName) {
        // Подключение к базе данных SQLite
        String url = "jdbc:sqlite:" + databaseName;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("!!!!!!!!!!!!Не удалось подключиться к базе данных: " + e.getMessage());
        }
    }
    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();

        String query = "SELECT name, line, longitude, latitude FROM stations";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Создаем массив объектов Station
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String line = resultSet.getString("line");
                String longitude = resultSet.getString("longitude");
                String latitude = resultSet.getString("latitude");

                stations.add(new Station(name, line, longitude, latitude));
            }
            return stations;


        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return stations;
    }





}

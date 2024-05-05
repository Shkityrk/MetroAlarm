package com.example.samsungschoolproject.model;

public class DatabaseModel {
    private String name;
    private String database;

    public DatabaseModel(String name, String database) {
        this.name = name;
        this.database = database;
    }

    public String getName() {
        return name;
    }

    public String getDatabase() {
        return database;
    }
}
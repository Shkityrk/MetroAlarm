package com.example.samsungschoolproject.model;

public class Database {
    private String name;
    private String database;
    private String nameGET;

    public Database(String name, String nameGET, String database) {
        this.name = name;
        this.nameGET = nameGET;
        this.database = database;
    }

    public String getNameGET() {
        return nameGET;
    }

    public void setNameGET(String nameGET) {
        this.nameGET = nameGET;
    }

    public String getName() {
        return name;
    }

    public String getDatabase() {
        return database;
    }
}
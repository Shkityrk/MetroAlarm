package com.example.samsungschoolproject.model;

public class Station {
    private String name;
    private String line;
    private String longitude;

    private String latitude;

    public Station(String name, String line, String longitude,String latitude){
        this.name = name;
        this.line = line;
        this.longitude=longitude;
        this.latitude=latitude;

    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}

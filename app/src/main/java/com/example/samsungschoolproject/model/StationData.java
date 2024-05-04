package com.example.samsungschoolproject.model;

public class StationData {
    private String isFavourite;
    private String alarm;

    public StationData(String isFavourite, String alarm) {
        this.isFavourite = isFavourite;
        this.alarm = alarm;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public String getAlarm() {
        return alarm;
    }
}
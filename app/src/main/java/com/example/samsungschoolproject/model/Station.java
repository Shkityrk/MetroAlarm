package com.example.samsungschoolproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "stations")
public class Station {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_station")
    private int id;
    private String name;
    private String line;
    private String longitude;
    @ColumnInfo(name = "width")
    private String latitude;

    public Station(int id, String name, String line, String longitude, String latitude){
        this.id = id;
        this.name = name;
        this.line = line;
        this.longitude=longitude;
        this.latitude=latitude;

    }

    public int getId() {
        return id;
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

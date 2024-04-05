package com.example.samsungschoolproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

@Entity(tableName = "station_neighbors")
public class Station {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_station")
    private int id;
    private String name;
    private String line;
    private String longitude;
    private String latitude;
    private String width_neighbour1;
    private String longitude_neighbour1;
    private String width_neighbour2;
    private String longitude_neighbour2;
    private String is_favourite;
    private String alarm;



    public Station(int id,
                   String name,
                   String line,
                   String latitude,
                   String longitude,
                   String width_neighbour1,
                   String longitude_neighbour1,
                   String width_neighbour2,
                   String longitude_neighbour2,
                   String is_favourite,
                   String alarm){
        this.id = id;
        this.name = name;
        this.line = line;
        this.longitude=longitude;
        this.latitude=latitude;
        this.width_neighbour1=width_neighbour1;
        this.longitude_neighbour1=longitude_neighbour1;
        this.width_neighbour2=width_neighbour2;
        this.longitude_neighbour2=longitude_neighbour2;
        this.is_favourite=is_favourite;
        this.alarm=alarm;


    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getNumLine() {
        return line;
    }

    public String getLine() { return line; }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }


    public String getLongitude_neighbour1() {
        return longitude_neighbour1;
    }

    public String getWidth_neighbour2() {
        return width_neighbour2;
    }

    public String getLongitude_neighbour2() {
        return longitude_neighbour2;
    }

    public String getIs_favourite() {
        return is_favourite;
    }
    public boolean getBoolFavourite() {
        return Objects.equals(is_favourite, "true");
    }

    public String getAlarm() {
        return alarm;
    }

    public boolean getBoolAlarm() {
        return Objects.equals(alarm, "true");
    }

    public String getWidth_neighbour1() {
        return width_neighbour1;
    }

    public void setAlarm(boolean isChecked) {
        if (isChecked) {
            this.alarm = "true";
        } else {
            this.alarm = "false";
        }
    }
}

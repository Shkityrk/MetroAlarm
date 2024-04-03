package com.example.samsungschoolproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

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

    public String getNumLine() {
        return line;
    }

    public String getLine() {
        HashMap lines = new HashMap();
        lines.put("1", "Сокольническая");
        lines.put("2", "Замоскворецкая");
        lines.put("3", "Арбатско-Покровская");
        lines.put("4", "Филевская");
        lines.put("5", "Кольцевая");
        lines.put("6", "Калужско-Рижская");
        lines.put("7", "Таганско-Краснопресненская");
        lines.put("8", "Калининско-Солнцевская");
        lines.put("9", "Серпуховско-Тимирязевская");
        lines.put("10", "Люблинско-Дмитровская");
        lines.put("11", "БКЛ");
        lines.put("12", "Бутовская");
        lines.put("13", "Монорельс");
        lines.put("МЦК", "МЦК");
        lines.put("15", "Некрасовская линия");
        lines.put("МЦД", "МЦД");
        return (String) lines.get(line);
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}

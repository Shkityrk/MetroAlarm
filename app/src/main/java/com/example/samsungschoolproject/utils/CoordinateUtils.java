package com.example.samsungschoolproject.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class CoordinateUtils {
    public static void checkCoordinatesInRadius(Context context, double latitude, double longitude, double targetLatitude, double targetLongitude, double radius) {
        double earthRadius = 6371000; // Радиус Земли в метрах
        double dLat = Math.toRadians(targetLatitude - latitude);
        double dLon = Math.toRadians(targetLongitude - longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(targetLatitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        if (distance <= radius) {
            Toast.makeText(context, "Координаты в пределах радиуса погрешности", Toast.LENGTH_SHORT).show();
        }
        Log.d("LocationService" , "Distance: " + distance + " meters");
    }
}

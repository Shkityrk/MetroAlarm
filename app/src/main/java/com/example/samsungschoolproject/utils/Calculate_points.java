package com.example.samsungschoolproject.utils;

public class Calculate_points {

    public static void main(String[] args) {
        double decimalDegree = 37.7786; // Пример десятичной записи широты или долготы

        String dms = decimalToDMS(decimalDegree);
        
    }

    public static String decimalToDMS(double decimalDegree) {
        int degrees = (int) decimalDegree;
        double minutesDouble = (decimalDegree - degrees) * 60;
        int minutes = (int) minutesDouble;
        double seconds = (minutesDouble - minutes) * 60;

        return degrees + "° " + minutes + "' " + seconds + "\"";
    }
}

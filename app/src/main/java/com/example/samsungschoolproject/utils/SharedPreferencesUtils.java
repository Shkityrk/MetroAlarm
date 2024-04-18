package com.example.samsungschoolproject.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

public class SharedPreferencesUtils {
    private Context mContext; // Add a Context member variable
    private static final String PREF_NAME = "station_preferences";
    private SharedPreferences preferences;

    public SharedPreferencesUtils(Context context) {
        mContext = context; // Assign the context passed to the constructor to mContext
        preferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }


    public void saveRingtonePath(Uri uri) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ringtonePath", uri.toString());
        editor.apply();

        Log.d("ringtonePath", "saveSettings: " + sharedPreferences.getString("ringtonePath", null));
        Toast.makeText(mContext, "Настройки сохранены", Toast.LENGTH_LONG).show();
    }

    public String getRingtonePath() {
        String selectedRingtonePath;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        selectedRingtonePath = sharedPreferences.getString("ringtonePath", null);
        Log.d("ringtonePath", "getRingtonePath: " + selectedRingtonePath);
        return selectedRingtonePath;
    }

    public void saveVolume(int volume) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("volume", volume);
        editor.apply();

        Log.d("volume", "saveVolume: " + sharedPreferences.getInt("volume", 0));
        Toast.makeText(mContext, "Настройки сохранены", Toast.LENGTH_LONG).show();
    }

    public int getVolume() {
        int volume;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        volume = sharedPreferences.getInt("volume", 0);
        Log.d("volume", "getVolume: " + volume);
        return volume;
    }

    public void saveVersion(String version) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("version", version);
        editor.apply();
    }

    public String getVersion() {
        String version;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        version = sharedPreferences.getString("version", null);
        return version;
    }

    public void saveVibration(boolean vibration) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("vibration", vibration);
        editor.apply();
    }

    public boolean getVibration() {
        boolean vibration;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        vibration = sharedPreferences.getBoolean("vibration", false);
        return vibration;
    }

    public void saveRadius(int radius) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("radius", radius);
        editor.apply();
    }

    public int getRadius() {
        int radius;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        radius = sharedPreferences.getInt("radius", 0);
        return radius;
    }

    public Map<String, ?> getAllStations() {
        return preferences.getAll();
    }

    public void saveStation(String stationName, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(stationName, value);
        editor.apply();
    }
}

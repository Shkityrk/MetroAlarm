package com.example.samsungschoolproject.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

/**
 * Утилитарный класс для работы с SharedPreferences.
 */
public class SharedPreferencesUtils {
    private Context mContext; // Add a Context member variable
    private static final String PREF_NAME = "station_preferences";
    private SharedPreferences preferences;

    /**
     * Конструктор класса SharedPreferencesUtils.
     *
     * @param context Контекст приложения.
     */
    public SharedPreferencesUtils(Context context) {
        mContext = context; // Assign the context passed to the constructor to mContext
        preferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    /**
     * Сохраняет путь к звонку в SharedPreferences.
     *
     * @param uri URI звонка.
     */
    public void saveRingtonePath(Uri uri) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ringtonePath", uri.toString());
        editor.apply();

        Log.d("ringtonePath", "saveSettings: " + sharedPreferences.getString("ringtonePath", null));
        Toast.makeText(mContext, "Настройки сохранены", Toast.LENGTH_LONG).show();
    }

    /**
     * Возвращает сохраненный путь к звонку из SharedPreferences.
     *
     * @return Путь к звонку.
     */
    public String getRingtonePath() {
        String selectedRingtonePath;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        selectedRingtonePath = sharedPreferences.getString("ringtonePath", null);
        return selectedRingtonePath;
    }
    /**
     * Сохраняет уровень громкости в SharedPreferences.
     *
     * @param volume Уровень громкости.
     */
    public void saveVolume(int volume) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("volume", volume);
        editor.apply();

        Log.d("volume", "saveVolume: " + sharedPreferences.getInt("volume", 0));
        Toast.makeText(mContext, "Настройки сохранены", Toast.LENGTH_LONG).show();
    }
    /**
     * Возвращает сохраненный уровень громкости из SharedPreferences.
     *
     * @return Уровень громкости.
     */
    public int getVolume() {
        int volume;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        volume = sharedPreferences.getInt("volume", 0);
        Log.d("volume", "getVolume: " + volume);
        return volume;
    }

    /**
     * Сохраняет версию в SharedPreferences.
     *
     * @param version Версия.
     */
    public void saveVersion(String version) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("version", version);
        editor.apply();
    }

    /**
     * Возвращает сохраненную версию из SharedPreferences.
     *
     * @return Версия.
     */
    public String getVersion() {
        String version;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        version = sharedPreferences.getString("version", null);
        return version;
    }

    /**
     * Сохраняет состояние вибрации в SharedPreferences.
     *
     * @param vibration Состояние вибрации.
     */
    public void saveVibration(boolean vibration) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("vibration", vibration);
        editor.apply();
    }

    /**
     * Возвращает сохраненное состояние вибрации из SharedPreferences.
     *
     * @return Состояние вибрации.
     */
    public boolean getVibration() {
        boolean vibration;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        vibration = sharedPreferences.getBoolean("vibration", false);
        return vibration;
    }

    /**
     * Сохраняет радиус в SharedPreferences.
     *
     * @param radius Радиус.
     */
    public void saveRadius(int radius) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("radius", radius);
        editor.apply();
    }

    /**
     * Возвращает сохраненный радиус из SharedPreferences.
     *
     * @return Радиус.
     */
    public int getRadius() {
        int radius;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        radius = sharedPreferences.getInt("radius", 0);
        return radius;
    }

    /**
     * Возвращает все станции из SharedPreferences.
     *
     * @return Карта со всеми станциями.
     */
    public Map<String, ?> getAllStations() {
        return preferences.getAll();
    }

    /**
     * Сохраняет информацию о станции в SharedPreferences.
     *
     * @param stationName Название станции.
     * @param value       Значение станции.
     */
    public void saveStation(String stationName, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(stationName, value);
        editor.apply();
    }

    /**
     * Устанавливает состояние запуска сервиса в SharedPreferences.
     *
     * @param isRunning Состояние запуска сервиса.
     */
    public void setServiceRunning(boolean isRunning) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("serviceRunning", isRunning);
        editor.apply();
    }

    /**
     * Возвращает состояние запуска сервиса из SharedPreferences.
     *
     * @return Состояние запуска сервиса.
     */
    public boolean getServiceRunning() {
        return preferences.getBoolean("serviceRunning", false);
    }

    /**
     * Устанавливает название станции, сработавшей в SharedPreferences.
     *
     * @param name Название станции.
     */
    public void setStationTriggeredName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("stationTriggeredName", name);
        editor.apply();
    }

    /**
     * Возвращает название станции, сработавшей из SharedPreferences.
     *
     * @return Название станции.
     */
    public String getStationTriggeredName(){
        return preferences.getString("stationTriggeredName", "ошибка");
    }

    /**
     * Устанавливает флаг первого запуска в SharedPreferences.
     *
     * @param isFirstStart Флаг первого запуска.
     */
    public void setFirstStart(boolean isFirstStart){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("FirstStart", isFirstStart);
        editor.apply();
    }

    /**
     * Возвращает флаг первого запуска из SharedPreferences.
     *
     * @return Флаг первого запуска.
     */
    public boolean getFirstStart(){
        return preferences.getBoolean("FirstStart", true);
    }

    /**
     * Сохраняет название данных в SharedPreferences.
     *
     * @param map Название данных.
     */
    public void setDataName(String map){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("databaseMap", map);
        editor.apply();
    }

    /**
     * Возвращает сохраненное название данных из SharedPreferences.
     *
     * @return Название данных.
     */
    public String getDataName(){
        return preferences.getString("databaseMap", "Метро");

    }

    /**
     * Сохраняет название базы данных в SharedPreferences.
     *
     * @param map Название базы данных.
     */
    public void setDatabaseMap(String map){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dbMap", map);
        editor.apply();
    }

    /**
     * Возвращает сохраненное название базы данных из SharedPreferences.
     *
     * @return Название базы данных.
     */
    public String getDatabaseMap(){
        return preferences.getString("dbMap", "MoscowMetro");
    }
}

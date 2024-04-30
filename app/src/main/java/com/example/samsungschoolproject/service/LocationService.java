package com.example.samsungschoolproject.service;



import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.data.StationRepository;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.model.Station;
import com.example.samsungschoolproject.utils.AlarmReceiver;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class LocationService extends IntentService {
    private static final String TAG = "LocationService";

    private static final int LOCATION_REQUEST_CODE = 1001;
    private static final long UPDATE_INTERVAL = 5000; // 5 seconds
    private static final long FASTEST_INTERVAL = 2000; // 2 seconds

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private List<Station> alarmStations;

    public LocationService() {
        super("BACK_LOCATION");
    }

    @Override
    public void onCreate() {

        super.onCreate();

        observeAlarmStations();

//        double targetLatitude = 55.8322; // Здесь задайте целевые координаты
//        double targetLongitude = 37.5110672; // Здесь задайте целевые координаты



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
//                for (Location location : locationResult.getLocations()) {
//                    Log.d(TAG, "Location: " + location.getLatitude() + ", " + location.getLongitude());
//                    checkCoordinatesInRadius(getApplicationContext(), location.getLatitude(), location.getLongitude(), targetLatitude, targetLongitude, radius);
//
//
//
//                }

                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG, "Location: " + location.getLatitude() + ", " + location.getLongitude());
                    // Проверяем каждую станцию из списка
                    for (Station station : alarmStations) {
                        if(station.getLongitude_neighbour1()!=null && station.getWidth_neighbour1()!=null){
                            checkCoordinatesInRadius(getApplicationContext(), location.getLatitude(), location.getLongitude(), Double.parseDouble(station.getLongitude_neighbour1()), Double.parseDouble(station.getWidth_neighbour1()), station.getName());
//
                        }
                        if(station.getLongitude_neighbour2()!=null && station.getWidth_neighbour2()!=null){
                            checkCoordinatesInRadius(getApplicationContext(), location.getLatitude(), location.getLongitude(), Double.parseDouble(station.getLongitude_neighbour2()), Double.parseDouble(station.getWidth_neighbour2()), station.getName());
//
                        }


                    }
                }
            }
        };
    }

    @SuppressLint({"NewApi", "MissingPermission", "ForegroundServiceType"})
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (checkLocationPermission()) {
            startLocationUpdates();
        }
        startForeground(1, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        return START_STICKY;
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Handle intent here if needed
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainMenuFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("Aboba Service")
                .setContentText("Running in background")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Установка канала уведомлений для Android Oreo и выше
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        return builder.build();
    }

    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Создаем интент для передачи в AlarmReceiver
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Устанавливаем будильник
        // В данном примере будильник запускается через 1 секунду
        long futureInMillis = System.currentTimeMillis() + 1000; // 1 second
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }


    public void checkCoordinatesInRadius(Context context, double latitude, double longitude, double targetLatitude, double targetLongitude, String name) {
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context);

        double radius; // Задайте радиус погрешности в метрах
        radius = sharedPreferencesUtils.getRadius()*15;
        Log.d(TAG, "checkCoordinatesInRadius: "+radius);

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

            if (fusedLocationClient != null && locationCallback != null) {
                fusedLocationClient.removeLocationUpdates(locationCallback);
            }
            sharedPreferencesUtils.setStationTriggeredName(name);
            setAlarm();

            sharedPreferencesUtils.setServiceRunning(false);


            stopSelf();

        }
        Log.d("LocationService" , "Distance: " + distance + " meters" + " "+ name);
    }

    public void observeAlarmStations() {

        StationRepository stationRepository = new StationRepository(getApplication());
        stationRepository.getAlarmStations().observeForever(new Observer<List<Station>>() {
            @Override
            public void onChanged(List<Station> stations) {
                Log.d(TAG, "Start printing alarmed stations");
                if (stations != null) {
                    alarmStations = stations;
                    for (Station station : stations) {
                        Log.d("StationInfo", "Название: " + station.getName() + ", width_neighbour1: " + station.getWidth_neighbour1());
                    }
                }
                Log.d(TAG, "Stop printing alarmed stations");
            }
        });
    }

//    public List<Station> getAlarmStationsList() {
//        final List<Station> alarmStationsList = new ArrayList<>();
//        StationRepository stationRepository = new StationRepository(getApplication());
//        stationRepository.getAlarmStations().observeForever(new Observer<List<Station>>() {
//            @Override
//            public void onChanged(List<Station> stations) {
//                if (stations != null) {
//                    alarmStationsList.clear();
//                    alarmStationsList.addAll(stations);
//                }
//            }
//        });
//        return alarmStationsList;
//    }





}
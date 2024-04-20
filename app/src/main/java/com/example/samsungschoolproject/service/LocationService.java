package com.example.samsungschoolproject.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
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
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.activity.MainActivity;
import com.example.samsungschoolproject.data.StationRepository;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.model.Station;
import com.example.samsungschoolproject.utils.AlarmReceiver;
import com.example.samsungschoolproject.utils.CoordinateUtils;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import java.util.List;

//public class LocationService extends IntentService {
//    private static final String TAG = "LocationService";
//    private static final int LOCATION_REQUEST_CODE = 1001;
//    private static final long UPDATE_INTERVAL = 5000; // 5 seconds
//    private static final long FASTEST_INTERVAL = 2000; // 2 seconds
//
//    private FusedLocationProviderClient fusedLocationClient;
//    private LocationCallback locationCallback;
//
//
//    public LocationService() {
//        super("BACK_LOCATION");
//    }
//    private boolean checkLocationPermission() {
//        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//    }
//
//
//    @Override
//    public void onCreate() {
//        createAlarmStationList(new OnDataFetchedListener() {
//            @Override
//            public void onDataFetched(List<Station> alarmStations) {
//                Log.d(TAG, "Alarm stations loaded: " + alarmStations.size());
//            }
//        });
//        initializeLocationUpdates();
//    }
//
//
//    @SuppressLint({"NewApi", "MissingPermission", "ForegroundServiceType"})
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (checkLocationPermission()) {
//            startLocationUpdates();
//        }
//        startForeground(1, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
//        return START_STICKY;
//    }
//
//    @SuppressLint("MissingPermission")
//    private void startLocationUpdates() {
//        LocationRequest locationRequest = LocationRequest.create()
//                .setInterval(UPDATE_INTERVAL)
//                .setFastestInterval(FASTEST_INTERVAL)
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                for (Location location : locationResult.getLocations()) {
//                    Log.d(TAG, "Location: " + location.getLatitude() + ", " + location.getLongitude());
//
//                    double targetLatitude = 55.8360472; // Здесь задайте целевые координаты
//                    double targetLongitude = 37.5060672; // Здесь задайте целевые координаты
//                    double radius = 1200.0; // Задайте радиус погрешности в метрах
//
//                    checkCoordinatesInRadius(getApplicationContext(), location.getLatitude(), location.getLongitude(), targetLatitude, targetLongitude, radius);
//                }
//            }
//        };
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        // Handle intent here if needed
//    }
//
//    public void onDestroy() {
//        Log.d(TAG, "onDestroy: called");
//        super.onDestroy();
//        if (fusedLocationClient != null && locationCallback != null) {
//            Log.d(TAG, "Removing location updates");
//            fusedLocationClient.removeLocationUpdates(locationCallback);
//        } else {
//            Log.e(TAG, "fusedLocationClient or locationCallback is null");
//        }
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//    private Notification createNotification() {
//        Intent notificationIntent = new Intent(this, MainMenuFragment.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
//                .setContentTitle("Aboba Service")
//                .setContentText("Running in background")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Установка канала уведомлений для Android Oreo и выше
//            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        return builder.build();
//    }
//
//    public void checkCoordinatesInRadius(Context context, double latitude, double longitude, double targetLatitude, double targetLongitude, double radius) {
//        double earthRadius = 6371000; // Радиус Земли в метрах
//        double dLat = Math.toRadians(targetLatitude - latitude);
//        double dLon = Math.toRadians(targetLongitude - longitude);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(targetLatitude)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = earthRadius * c;
//
//        if (distance <= radius) {
//            Toast.makeText(context, "Координаты в пределах радиуса погрешности", Toast.LENGTH_SHORT).show();
//
//            if (fusedLocationClient != null && locationCallback != null) {
//                fusedLocationClient.removeLocationUpdates(locationCallback);
//            }
//
//            setAlarm(); // Если все хорошо, запускаем будильник
//            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context);
//            sharedPreferencesUtils.setServiceRunning(false);
//
//
//            stopSelf();
//
//        }
//        Log.d("LocationService" , "Distance: " + distance + " meters");
//    }
//
//
//    private void setAlarm() {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        // Создаем интент для передачи в AlarmReceiver
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Устанавливаем будильник
//        // В данном примере будильник запускается через 10 секунд
//        long futureInMillis = System.currentTimeMillis() + 10000; // 10 seconds
//        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
//    }
//
//    public void createAlarmStationList(final OnDataFetchedListener listener) {
//        StationRepository stationRepository = new StationRepository(getApplication());
//        LiveData<List<Station>> alarmStationsLiveData = stationRepository.getAlarmStations();
//        alarmStationsLiveData.observeForever(new Observer<List<Station>>() {
//            @Override
//            public void onChanged(List<Station> alarmStations) {
//                if (alarmStations != null) {
//                    listener.onDataFetched(alarmStations);
//                }
//                alarmStationsLiveData.removeObserver(this);
//            }
//        });
//    }
//
//
//    public interface OnDataFetchedListener {
//        void onDataFetched(List<Station> alarmStations);
//    }
//
//    private void initializeLocationUpdates() {
//        if (checkLocationPermission()) {
//            startLocationUpdates();
//        }
//    }
//
//}

public class LocationService extends IntentService {
    private static final String TAG = "LocationService";

    private static final int LOCATION_REQUEST_CODE = 1001;
    private static final long UPDATE_INTERVAL = 5000; // 5 seconds
    private static final long FASTEST_INTERVAL = 2000; // 2 seconds

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean serviceRunning = false; // Track service state


    public LocationService() {
        super("BACK_LOCATION");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Retrieve service status from shared preferences
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this);
        serviceRunning = sharedPreferencesUtils.getServiceRunning();
        if (serviceRunning) {
            initializeLocationUpdates();
        }
//        createAlarmStationList(new OnDataFetchedListener() {
//            @Override
//            public void onDataFetched(List<Station> alarmStations) {
//                Log.d(TAG, "Alarm stations loaded: " + alarmStations.size());
//
//                // Здесь будет ваша логика обработки полученного списка станций сигнализации
//                // Например, вы можете сохранить полученный список станций в поле класса или передать его в другой метод.
//            }
//        });
//        initializeLocationUpdates();
    }

    @SuppressLint({"NewApi", "MissingPermission", "ForegroundServiceType"})
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Update service status when started
        serviceRunning = true;
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this);
        sharedPreferencesUtils.setServiceRunning(true);

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG, "Location: " + location.getLatitude() + ", " + location.getLongitude());
                    // Здесь вы можете добавить вашу логику, используя полученное местоположение и список станций сигнализации.
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Handle intent here if needed
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Update service status when stopped
        serviceRunning = false;
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this);
        sharedPreferencesUtils.setServiceRunning(false);

        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("Aboba Service")
                .setContentText("Running in background")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        return builder.build();
    }

    public void createAlarmStationList(final OnDataFetchedListener listener) {
        StationRepository stationRepository = new StationRepository(getApplication());
        LiveData<List<Station>> alarmStationsLiveData = stationRepository.getAlarmStations();
        alarmStationsLiveData.observeForever(new Observer<List<Station>>() {
            @Override
            public void onChanged(List<Station> alarmStations) {
                if (alarmStations != null) {
                    listener.onDataFetched(alarmStations);
                }
                alarmStationsLiveData.removeObserver(this);
            }
        });
    }

    public interface OnDataFetchedListener {
        void onDataFetched(List<Station> alarmStations);
    }

    private void initializeLocationUpdates() {
        if (checkLocationPermission()) {
            startLocationUpdates();
        }
    }
}


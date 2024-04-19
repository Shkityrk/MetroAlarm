package com.example.samsungschoolproject.service;

//
//import android.Manifest;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.IBinder;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//
//public class GpsService extends Service {
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//
//    private FusedLocationProviderClient fusedLocationClient;
//    private LocationCallback locationCallback;
//    public GpsService() {
//    }
//    private boolean isRunning;
//    private Thread backgroundThread;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Context context = getApplicationContext();
//        isRunning = false;
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    Log.d("BLAAA", "Dont work");
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    Log.d("LocationBackground", "Lat: " + location.getLatitude() + ", Long: " + location.getLongitude());
//                    String message = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (!isRunning) {
//            isRunning = true;
//            requestLocationUpdates();
//            startBackgroundThread();
//        }
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        stopBackgroundThread();
//    }
//
//    private void startBackgroundThread() {
//        backgroundThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // Your looping task goes here
//                while (isRunning) {
//
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        backgroundThread.start();
//    }
//
//    private void stopBackgroundThread() {
//        isRunning = false;
//        if (backgroundThread != null) {
//            try {
//                backgroundThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    private void requestLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED) {
//            stopSelf(); // Stop the service if permission is not granted
//            return;
//        }
//
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(3000); // Update location every 10 seconds
//        locationRequest.setFastestInterval(2000); // The fastest interval for location updates
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
////        fusedLocationClient.requestLocationUpdates(locationRequest,
////                locationCallback,
////                null /* Looper */);
//    }
//
//}



import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.activity.MainActivity;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.utils.CoordinateUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends IntentService {
    private static final String TAG = "LocationService";

    private static final int LOCATION_REQUEST_CODE = 1001;
    private static final long UPDATE_INTERVAL = 5000; // 5 seconds
    private static final long FASTEST_INTERVAL = 2000; // 2 seconds

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    public LocationService() {
        super("BACK_LOCATION");
    }

    @Override
    public void onCreate() {

        super.onCreate();

        double targetLatitude = 55.8360472; // Здесь задайте целевые координаты
        double targetLongitude = 37.5060672; // Здесь задайте целевые координаты
        double radius = 300.0; // Задайте радиус погрешности в метрах

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG, "Location: " + location.getLatitude() + ", " + location.getLongitude());
                    CoordinateUtils.checkCoordinatesInRadius(getApplicationContext(), location.getLatitude(), location.getLongitude(), targetLatitude, targetLongitude, radius);
                    // Handle location updates here
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

}

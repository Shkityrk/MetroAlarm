//package com.example.samsungschoolproject.service;
//
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
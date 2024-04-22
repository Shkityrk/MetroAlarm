package com.example.samsungschoolproject.activity;


import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.service.MusicService;
import com.example.samsungschoolproject.utils.NotificationHelper;

import android.media.MediaPlayer;

public class AlarmActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "AlarmChannel";
    private static final String CHANNEL_NAME = "Alarm Notifications";

    private TextView textView;
    private Button dismissButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Initialize UI components
        textView = findViewById(R.id.textView);
        dismissButton = findViewById(R.id.dismissButton);

        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);

        // Create notification channel (required for Android 8.0 and higher)
        createNotificationChannel();

        // Build the notification
        Notification notification = buildNotification();

        // Display the notification
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(NOTIFICATION_ID, notification);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the notification
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.cancel(NOTIFICATION_ID);

                // Stop the music service
                Intent stopServiceIntent = new Intent(AlarmActivity.this, MusicService.class);
                stopService(stopServiceIntent);

                // Close the activity
                finish();
            }
        });

        // Check if the screen is locked and wake it up
        PowerManager pm = getSystemService(PowerManager.class);
        if (pm != null && pm.isInteractive()) {
            PowerManager.WakeLock wakeLock = pm.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                            | PowerManager.ACQUIRE_CAUSES_WAKEUP
                            | PowerManager.ON_AFTER_RELEASE,
                    "MyApp:MyWakeLockTag");
            wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the music service
        Intent stopServiceIntent = new Intent(this, MusicService.class);
        stopService(stopServiceIntent);
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = "Channel for alarm notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildNotification() {
        Intent stopIntent = new Intent(this, MusicService.class);
        stopIntent.setAction("STOP");
        PendingIntent stopPendingIntent = PendingIntent.getService(
                this,
                0,
                stopIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Playing Music")
                .setContentText("Tap to stop music")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentIntent(stopPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)  // Close the notification when tapped
                .build();
    }
}
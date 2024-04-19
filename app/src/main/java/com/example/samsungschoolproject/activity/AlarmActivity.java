package com.example.samsungschoolproject.activity;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.service.MusicService;
import com.example.samsungschoolproject.utils.NotificationHelper;

import android.media.MediaPlayer;

public class AlarmActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    private WindowManager windowManager;
    private TextView textView;
    private Button dismissButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Initialize UI components
        textView = new TextView(this);
        textView.setText("Your alarm message here");
        dismissButton = new Button(this);
        dismissButton.setText("Dismiss");

        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);

        Notification notification = NotificationHelper.createNotification(this);

        // Отображение уведомления
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

        // Set up the window layout parameters
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                android.graphics.PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(textView, params);

        // Add button to dismiss the alarm
        WindowManager.LayoutParams buttonParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                android.graphics.PixelFormat.TRANSLUCENT);
        buttonParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        windowManager.addView(dismissButton, buttonParams);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Удаление уведомления
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(NOTIFICATION_ID);

                // Остановка музыкального сервиса
                Intent stopServiceIntent = new Intent(AlarmActivity.this, MusicService.class);
                stopService(stopServiceIntent);

                // Закрытие активности
                finish();
            }
        });

        // Check if the screen is locked and wake it up
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (km.isKeyguardLocked()) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
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
        if (windowManager != null) {
            if (textView != null) {
                windowManager.removeViewImmediate(textView);
            }
            if (dismissButton != null) {
                windowManager.removeViewImmediate(dismissButton);
            }
        }
        // Stop music service
        Intent stopServiceIntent = new Intent(this, MusicService.class);
        stopService(stopServiceIntent);
    }
}

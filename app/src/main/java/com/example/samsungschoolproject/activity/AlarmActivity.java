package com.example.samsungschoolproject.activity;

import android.annotation.SuppressLint;
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

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.service.MusicService;
import com.example.samsungschoolproject.utils.NotificationHelper;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;

import android.media.MediaPlayer;

import java.lang.ref.WeakReference;

public class AlarmActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;

    private TextView textView;
    private Button dismissButton;
    private MediaPlayer mediaPlayer;
    private WeakReference<MainMenuFragment> reference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        SharedPreferencesUtils sharedPreferencesUtils = new  SharedPreferencesUtils(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Initialize UI components
        textView = findViewById(R.id.textView);
        dismissButton = findViewById(R.id.dismissButton);

        textView.setText( sharedPreferencesUtils.getStationTriggeredName());

        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);

        Notification notification = NotificationHelper.createNotification(this);

        // Отображение уведомления
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);


        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Удаление уведомления
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(NOTIFICATION_ID);

                // Остановка музыкального сервиса
                Intent stopServiceIntent = new Intent(AlarmActivity.this, MusicService.class);
                stopService(stopServiceIntent);

                MainMenuFragment mainMenuFragment = new MainMenuFragment();
                mainMenuFragment.setStartFalseAfterAlarm();

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

        // Stop music service
        Intent stopServiceIntent = new Intent(this, MusicService.class);
        stopService(stopServiceIntent);
    }


    public void setReference(MainMenuFragment fragment){
        reference = new WeakReference<>(fragment);
    }

    private WeakReference<MainMenuFragment> getReference() {
        return reference;
    }
}

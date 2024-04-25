package com.example.samsungschoolproject.service;

import static java.security.AccessController.getContext;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    Uri content_uri = Uri.parse("android.resource://com.example.samsungschoolproject/" + R.raw.song);

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate, начальная мелодия "+content_uri);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        SharedPreferencesUtils sharedPreferencesUtils =new SharedPreferencesUtils(getApplicationContext());
        mediaPlayer.setVolume( sharedPreferencesUtils.getVolume() / 100f,sharedPreferencesUtils.getVolume() / 100f );

        if (sharedPreferencesUtils.getRingtonePath()!=null){
            content_uri=Uri.parse(sharedPreferencesUtils.getRingtonePath());
            try {
                Log.d(TAG, "измнение мелодии на "+content_uri);



                Context context = getApplicationContext();
                ContentResolver contentResolver = context.getContentResolver();
                try {
                    Cursor cursor = contentResolver.query(content_uri, null, null, null, null);
                    if (cursor != null) {
                        cursor.close();
                        Log.d(TAG, "проверка на доступность файла:cursor доступен");
                    }
                } catch (SecurityException e) {
                    Log.d(TAG, "cursor не доступен");
                    e.printStackTrace();
                }

                Log.d(TAG, "установка DataSource");
                mediaPlayer.setDataSource(this, content_uri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            try {
                mediaPlayer.setDataSource(this, content_uri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.setLooping(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                // Устанавливаем паттерн вибрации: 2 секунды вибрации, затем пауза в 0.5 секунды, повторяем
                long[] pattern = {0, 2000, 500};
                vibrator.vibrate(pattern, 0);
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();

        // Останавливаем вибрацию при завершении работы сервиса
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
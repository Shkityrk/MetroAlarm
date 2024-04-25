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
    Uri content_uri = Uri.parse("android.resource://com.example.samsungschoolproject/" + R.raw.song);

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ringtonePath--", "Start Media: " + content_uri);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        SharedPreferencesUtils sharedPreferencesUtils =new SharedPreferencesUtils(getApplicationContext());
        mediaPlayer.setVolume( sharedPreferencesUtils.getVolume() / 100f,sharedPreferencesUtils.getVolume() / 100f );// Установка аудиопотока

        if (sharedPreferencesUtils.getRingtonePath()!=null){
            content_uri=Uri.parse(sharedPreferencesUtils.getRingtonePath());
            try {
//                AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(content_uri, "r");
                Log.d("ringtonePath--", "create alarm");

                Context context = getApplicationContext();

                ContentResolver contentResolver = context.getContentResolver();
                try {
                    // Пытаемся получить информацию о файле
                    Cursor cursor = contentResolver.query(content_uri, null, null, null, null);
                    if (cursor != null) {
                        cursor.close();
                        Log.d("ringtonePath--", "cursor доступен");
                    }
                } catch (SecurityException e) {
                    Log.d("ringtonePath--", "cursor не доступен");
                    e.printStackTrace();
                }


                mediaPlayer.setDataSource(this, content_uri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            try {
                AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.song);
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.setLooping(true); // Для повторного воспроизведения мелодии
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
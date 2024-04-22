package com.example.samsungschoolproject.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;

import com.example.samsungschoolproject.R;

public class MusicService extends Service {


    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.song); // Замените "your_audio_file" на название вашего аудиофайла
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
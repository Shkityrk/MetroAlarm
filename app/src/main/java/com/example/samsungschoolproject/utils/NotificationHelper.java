package com.example.samsungschoolproject.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.samsungschoolproject.R;

/**
 * Утилитарный класс для создания уведомлений.
 */
public class NotificationHelper {
    private static final String CHANNEL_ID = "MusicChannel";
    private static final String CHANNEL_NAME = "Music Notifications";

    /**
     * Создает и возвращает уведомление.
     *
     * @param context Контекст приложения.
     * @return Объект уведомления.
     */
    public static Notification createNotification(Context context) {
        createNotificationChannel(context);

        Intent swipeIntent = new Intent(context, NotificationReceiver.class);
        swipeIntent.setAction("SWIPE");
        PendingIntent swipePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                swipeIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );


        Intent stopIntent = new Intent(context, NotificationReceiver.class);
        stopIntent.setAction("STOP");
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                stopIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Станция!") // Заголовок уведомления
                .setContentText("Будильник сработал!") // Текст уведомления
                .setSmallIcon(R.drawable.picture_metro_icon_app) // Иконка уведомления
                .setContentIntent(stopPendingIntent) // Намерение при нажатии на уведомление
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Приоритет уведомления
                .setFullScreenIntent(stopPendingIntent, true) // Показать на весь экран
                .setDeleteIntent(swipePendingIntent) // Намерение при свайпе уведомления
                .setVibrate(new long[] {100, 200, 300, 500, 100, 200, 300, 500}); // Вибрация


        builder.setDeleteIntent(stopPendingIntent);
        builder.setDeleteIntent(swipePendingIntent);
        return builder.build();
    }
    /**
     * Создает канал уведомлений.
     *
     * @param context Контекст приложения.
     */
    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Music Notification";
            String description = "Control Music Playback";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
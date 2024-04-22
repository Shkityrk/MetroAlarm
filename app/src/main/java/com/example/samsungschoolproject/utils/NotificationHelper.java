package com.example.samsungschoolproject.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MusicChannel";
    private static final String CHANNEL_NAME = "Music Notifications";


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
                .setContentTitle("Playing Music")
                .setContentText("Tap to stop music")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentIntent(stopPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(stopPendingIntent, true)
                .setDeleteIntent(swipePendingIntent)
                .setVibrate(new long[] {100, 200, 300, 500, 100, 200, 300, 500});


        builder.setDeleteIntent(stopPendingIntent);
        builder.setDeleteIntent(swipePendingIntent);
        return builder.build();
    }

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
package com.example.samsungschoolproject.utils;



import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.samsungschoolproject.service.MusicService;

/**
 * BroadcastReceiver для обработки действий из уведомлений.
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Обработка действия "STOP" - остановка сервиса и удаление уведомления
        if (intent.getAction().equals("STOP")) {
            Intent stopServiceIntent = new Intent(context, MusicService.class);
            context.stopService(stopServiceIntent);

            // Скрытие уведомления
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(NOTIFICATION_ID);
        }
        // Обработка действия "SWIPE" - только остановка сервиса
        if (intent.getAction().equals("SWIPE")) {
            Intent stopServiceIntent = new Intent(context, MusicService.class);
            context.stopService(stopServiceIntent);
        }
        // Обработка действия "CLOSE_ACTIVITY" - закрытие активности
        if (intent.getAction().equals("CLOSE_ACTIVITY")) {
            // Закрыть активность
            ((Activity) context).finish();
        }
    }
}
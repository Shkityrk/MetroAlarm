package com.example.samsungschoolproject.utils;

import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

public class ThemeUtils {
    public static boolean isDarkTheme(AppCompatActivity activity) {
        int nightModeFlags = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}

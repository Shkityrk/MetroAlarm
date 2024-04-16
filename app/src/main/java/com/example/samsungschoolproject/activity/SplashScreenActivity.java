package com.example.samsungschoolproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.utils.ThemeUtils;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageView imageView = findViewById(R.id.splashImageView);

        int imageResId;
        boolean isDark = ThemeUtils.isDarkTheme(this);
        if (isDark) {
            imageResId = R.drawable.splash_picture_metro_icon_app_dark;
        } else {
            imageResId = R.drawable.splash_picture_metro_icon_app;
        }

        imageView.setImageResource(imageResId);

        scheduleSplashScreen();
    }
    private void scheduleSplashScreen() {
        long splashScreenDuration = getSplashScreenDuration();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // После Splash Screen перенаправляем на нужную Activity
                routeToAppropriatePage();
                finish();
            }
        }, splashScreenDuration);
    }

    private long getSplashScreenDuration() {
        return 3500; // Время в миллисекундах (в данном случае 2 секунды)
    }

    private void routeToAppropriatePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
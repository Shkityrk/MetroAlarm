package com.example.samsungschoolproject.activity;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;
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


        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        if (sharedPreferencesUtils.getFirstStart()){
            sharedPreferencesUtils.setServiceRunning(false);
            // здесь должен быть фрагмент с обучением
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

        if(sharedPreferencesUtils.getServiceRunning()){
            MainMenuFragment mf = new MainMenuFragment();
            mf.stopLocationService();
            Log.d("LocationSerivce", "Stopped via SplashScreen");
            sharedPreferencesUtils.setServiceRunning(false);

        }

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
        return 2500; // Время в миллисекундах (в данном случае 2 секунды)
    }

    private void routeToAppropriatePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
package com.example.samsungschoolproject.activity;

import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;

import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<String> singlePermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted ->{
                        Log.d("perm", "perm granted");
                    }
            );

    ActivityResultLauncher<String[]> multiPermissionLauncher =
        registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                 callback ->{
                    for (Map.Entry<String, Boolean> entry : callback.entrySet()){
                        Log.d("Perm", entry.getKey()+" : "+ entry.getValue());
                    }
                });

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, mainMenuFragment);
        transaction.commit();

        multiPermissionLauncher.launch(
                new String[]{
                        Manifest.permission.READ_MEDIA_AUDIO,
                        Manifest.permission.MANAGE_MEDIA,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                }
        );
    }
//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onBackPressed() {
//        Log.d("MainActivity", "onBackPressed() called");
//        // Проверяем, есть ли фрагменты в стеке
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            Log.d("MainActivity", "Popping back stack");
//            // Если есть, возвращаемся к предыдущему фрагменту
//            getSupportFragmentManager().popBackStack();
//        } else {
//            Log.d("MainActivity", "No fragments in back stack, calling finish()");
//            // Если стек фрагментов пуст, закрываем активность
//            finish();
//        }
//    }





}
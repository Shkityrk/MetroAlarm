package com.example.samsungschoolproject.activity;

import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }




        getSupportFragmentManager().popBackStack();
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        DatabaseHelper.copyDatabaseFromAssets(this);
        transaction.add(R.id.container, mainMenuFragment);
        transaction.commit();

//        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
//            singlePermissionLauncher.launch(Manifest.permission.CAMERA);
        multiPermissionLauncher.launch(
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,

                }
        );


    }
}
package com.example.samsungschoolproject.activity;

import static com.example.samsungschoolproject.utils.NetworkUtils.disableSSLCertificateChecking;
import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.utils.NetworkUtils;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String version;


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
        getVersion();

        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, mainMenuFragment);
        transaction.commit();

        multiPermissionLauncher.launch(
                new String[]{
                        Manifest.permission.READ_MEDIA_AUDIO,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                }
        );

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (!notificationManager.areNotificationsEnabled()) {
            showToast("Необходимо разрешить уведомления!");
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            showToast("Необходимо разрешить использование поверх других приложений!");
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }

        if(!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
        }

    }


    public void getVersion() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://79.137.197.216/get_version";
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());

                version = sharedPreferencesUtils.getVersion();

                disableSSLCertificateChecking();
                String jsonData = NetworkUtils.getJSONFromServer(url);
                if (jsonData != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String versionName = jsonObject.getString("version");

                        if (!versionName.equals(version)) {
                            showToast("Доступна новая версия приложения");
                        }
                    } catch (JSONException e) {
                        Log.e("Error", "Error parsing JSON", e);
                    }
                }
            }
        });
        thread.start();
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }


}
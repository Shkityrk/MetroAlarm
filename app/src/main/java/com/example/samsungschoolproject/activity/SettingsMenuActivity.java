package com.example.samsungschoolproject.activity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samsungschoolproject.R;
//import com.example.samsungschoolproject.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.cdimascio.dotenv.Dotenv;

public class SettingsMenuActivity extends AppCompatActivity {
    private static final int PICK_RINGTONE_REQUEST = 1;
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = false;
    Uri content_uri = Uri.parse("android.resource://com.example.samsungschoolproject/" + R.raw.song);
    private int volume;
    private String version;
    private Context mContext;
    String database;

//    private final String SERVER_CHECK_VERSION = dotenv.get("SERVER_CHECK_VERSION");

    private static final String SERVER_URL_CHECK_VERSION = "http://79.137.197.216:8000/get_version/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        Button goBackButton = findViewById(R.id.back_to_main_menu);
        Button buttonSelectRingtone = findViewById(R.id.buttonSelectRingtone);  // Кнопка выбора рингтона
        SeekBar seekBarVolume = findViewById(R.id.seekBarVolume);  // Ползунок громкости
        Button buttonSaveSettings = findViewById(R.id.buttonSaveSettings);  // Кнопка сохранения настроек
        Button getServerVersion = findViewById(R.id.getServerVersion);

        Log.d("ringtonePath", "Start Media: " + content_uri);

        if (getRingtonePath()!=null){
            content_uri=Uri.parse(getRingtonePath());
        }
        Log.d("ringtonePath", content_uri.toString());

        if(getVersion()!=null){
            version = getVersion();
        }


        volume=getVolume();
        seekBarVolume.setProgress(volume);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        mediaPlayer.setVolume( volume / 100f,volume / 100f );

        Log.d("volume", String.valueOf(volume));


        goBackButton.setOnClickListener(new View.OnClickListener() {// Листенер для кнопки выхода в главное меню
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // Переместить MainActivity на передний план
                startActivity(intent);
                finish(); // Закрыть текущую активность настроек
            }
        });

        buttonSelectRingtone.setOnClickListener(new View.OnClickListener() {  // Листенер для кнопки выбора рингтона
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {  // Листенер для ползунка громкости
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("volume", "onProgressChanged: " + progress);
                saveVolume(progress);
                mediaPlayer.setVolume( progress / 100f,progress / 100f );

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {  // Листенер для кнопки сохранения настроек
            @Override
            public void onClick(View v) {
                volume = seekBarVolume.getProgress();
                Log.d("volume", "DONE");
                Log.d("volume", "volume: " + volume);



                if (v.getId() == R.id.buttonSaveSettings) {
                    // Проверяем, играет ли музыка
                    if (!isMusicPlaying) {
                        setMediaPlayer(content_uri);
                        mediaPlayer.start();
                        isMusicPlaying = true;
                    } else {
                        mediaPlayer.pause();
                        isMusicPlaying = false;
                    }
                }
            }
        });

        getServerVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchVersionTask().execute(SERVER_URL_CHECK_VERSION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_RINGTONE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    saveRingtonePath(uri);
                    content_uri= uri;

                    Toast.makeText(this, "Выбран рингтон: " + uri.getPath(), Toast.LENGTH_LONG).show();
//                    saveSettings(uri);
                    Log.d("ringtonePath", "onActivityResult: " + uri.getPath());
                }
            }
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_RINGTONE_REQUEST);
    }
    private void saveRingtonePath(Uri uri) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ringtonePath", uri.toString());
        editor.apply();

        Log.d("ringtonePath", "saveSettings: " + sharedPreferences.getString("ringtonePath", null));
        Toast.makeText(this, "Настройки сохранены", Toast.LENGTH_LONG).show();
    }

    private String getRingtonePath() {
        String selectedRingtonePath;
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        selectedRingtonePath = sharedPreferences.getString("ringtonePath", null);
        Log.d("ringtonePath", "getRingtonePath: " + selectedRingtonePath);
        return selectedRingtonePath;
    }

    private void setMediaPlayer(Uri uri_song) {
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri_song);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
    }

    private void saveVolume(int volume) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("volume", volume);
        editor.apply();

        Log.d("volume", "saveVolume: " + sharedPreferences.getInt("volume", 0));
        Toast.makeText(this, "Настройки сохранены", Toast.LENGTH_LONG).show();
    }

    private int getVolume() {
        int volume;
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        volume = sharedPreferences.getInt("volume", 0);
        Log.d("volume", "getVolume: " + volume);
        return volume;
    }

    private void saveVersion(String version) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("version", version);
        editor.apply();
    }

    private String getVersion() {
        String version;
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        version = sharedPreferences.getString("version", null);
        return version;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }


    private class FetchVersionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                Log.e("Error", "Error fetching data", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String versionName = jsonObject.getString("version");

                    if (versionName.equals(version)) {
                        Toast.makeText(getApplicationContext(), "У вас последняя версия", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Доступна новая версия: " + versionName, Toast.LENGTH_SHORT).show();
                        new DownloadDataTask().execute();  // Загрузка данных с сервера
                        //------------------------------------------------------
                    }
                    saveVersion(versionName);
                    Toast.makeText(getApplicationContext(), "Версия: " + versionName, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("Error", "Error parsing JSON", e);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Ошибка при получении данных", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class DownloadDataTask extends AsyncTask<Void, Void, String> {
        private static final String TAG = "DownloadDataTask";
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://79.137.197.216:8000/get_database/");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                result = stringBuilder.toString();
                Log.d("database", "downl");



            } catch (Exception e) {
                Log.e(TAG, "Error downloading data", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            database = result;
            return result;
        }

//        @Override
//        protected void onPostExecute(String result) {
//            FileUtils.replaceFile(database);
//        }
    }


}
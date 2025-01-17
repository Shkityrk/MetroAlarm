package com.example.samsungschoolproject.view.activity;
import static com.example.samsungschoolproject.network.NetworkUtils.disableSSLCertificateChecking;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.network.NetworkUtils;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SettingsMenuActivity extends AppCompatActivity {
    private static final int PICK_RINGTONE_REQUEST = 1;
    private static final String TAG = "SettingsMenuActivity";
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = false;
    Uri content_uri = Uri.parse("android.resource://com.example.samsungschoolproject/" + R.raw.song);
    private int volume;
    private String version;
    private boolean vibration;
    private Context mContext;

    String database;
//    private final String SERVER_CHECK_VERSION = dotenv.get("SERVER_CHECK_VERSION");

    private static final String SERVER_URL_CHECK_VERSION = "http://79.137.197.216:8000/get_version/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
        mContext = getApplicationContext();

//        Button goBackButton = findViewById(R.id.back_to_main_menu);
        Button buttonSelectRingtone = findViewById(R.id.buttonSelectRingtone);  // Кнопка выбора рингтона
        SeekBar seekBarVolume = findViewById(R.id.seekBarVolume);  // Ползунок громкости
        Button playStop = findViewById(R.id.buttonSaveSettings);  // Кнопка сохранения настроек
        Button getServerVersion = findViewById(R.id.getServerVersion);
        CheckBox vibration = findViewById(R.id.checkBoxVibration);
        SeekBar seekBarRadius = findViewById(R.id.seekBarRadius);
        TextView textViewRadiusValue = findViewById(R.id.textViewRadiusValue);


//        Button buttonDewMode = findViewById(R.id.button_devMenu);

        Log.d(TAG, "начальный путь к файлу: " + content_uri);

        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());

        if (sharedPreferencesUtils.getRingtonePath()!=null){
            content_uri=Uri.parse(sharedPreferencesUtils.getRingtonePath());
            Log.d(TAG, "путь изменяется на" +content_uri);
        }
        Log.d(TAG, "Итоговый путь" + content_uri.toString());
        Log.d(TAG, "Итоговый путь в строке" + content_uri);
        Log.d(TAG, "Основной путь" + content_uri.getPath());

        if(sharedPreferencesUtils.getVersion()!=null){
            version = sharedPreferencesUtils.getVersion();
        }


        vibration.setChecked(sharedPreferencesUtils.getVibration());

        seekBarRadius.setProgress(sharedPreferencesUtils.getRadius());


        volume=sharedPreferencesUtils.getVolume();
        seekBarVolume.setProgress(volume);
        Log.d(TAG, "создание медиаплеера");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        mediaPlayer.setVolume( volume / 100f,volume / 100f );

        Log.d("volume", String.valueOf(volume));

        textViewRadiusValue.setText("Радиус: " + sharedPreferencesUtils.getRadius()*15 + " метров");


//        goBackButton.setOnClickListener(new View.OnClickListener() {// Листенер для кнопки выхода в главное меню
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "остановка медиаплеера");
//                if (mediaPlayer != null) {
//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                }
//                Intent intent = new Intent(v.getContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // Переместить MainActivity на передний план
//                startActivity(intent);
//                finish(); // Закрыть текущую активность настроек
//            }
//        });

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
                sharedPreferencesUtils.saveVolume(progress);
                mediaPlayer.setVolume( progress / 100f,progress / 100f );

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        playStop.setOnClickListener(new View.OnClickListener() {  // Листенер для кнопки сохранения настроек
            @Override
            public void onClick(View v) {
                volume = seekBarVolume.getProgress();
                Log.d("volume", "DONE");
                Log.d("volume", "volume: " + volume);



                if (v.getId() == R.id.buttonSaveSettings) {
                    // Проверяем, играет ли музыка
                    Log.d(TAG, "проверяем, играет ли музыка");
                    if (!isMusicPlaying) {
                        playStop.setText("||");
                        setMediaPlayer(content_uri);
                        mediaPlayer.start();
                        isMusicPlaying = true;
                    } else {
                        mediaPlayer.pause();
                        playStop.setText("▶");
                        isMusicPlaying = false;
                    }
                }
            }
        });

        vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibration.isChecked()){
                    Toast.makeText(getApplicationContext(), "Вибрация включена", Toast.LENGTH_SHORT).show();
                    sharedPreferencesUtils.saveVibration(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Вибрация выключена", Toast.LENGTH_SHORT).show();
                    sharedPreferencesUtils.saveVibration(false);
                }
            }
        });

        getServerVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVersion();
            }
        });


        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int radiusValue = progress * 15;
                textViewRadiusValue.setText("Радиус: " + radiusValue + " метров");
                sharedPreferencesUtils.saveRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_RINGTONE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
                    sharedPreferencesUtils.saveRingtonePath(uri);
                    content_uri= uri;

                    showToast("Рингтон изменен");
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


    private void setMediaPlayer(Uri uri_song) {
        Log.d(TAG, "setMediaPlayer: Uri for media: " + uri_song.toString());
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(), uri_song);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
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

    public void getVersion() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://79.137.197.216/get_version";
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
                String map = sharedPreferencesUtils.getDatabaseMap();
                String urlDB = "https://79.137.197.216/get_station_data/?databaseApplication=StationModel&db_name="+map;
                disableSSLCertificateChecking();
                String jsonData = NetworkUtils.getJSONFromServer(url);
                if (jsonData != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String versionName = jsonObject.getString("version");

                        if (versionName.equals(version)) {
                            showToast("У вас последняя версия");
                        } else {
                            showToast("Доступна новая версия: " + versionName);
                            NetworkUtils networkUtils = new NetworkUtils();

                            networkUtils.updateDataFromJSON(urlDB, getApplication(), getApplicationContext());// Загрузка данных с сервера
                        }
                        version = versionName;
                        saveVersion(versionName);
//                        showToast("Версия: " + versionName);
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
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveVersion(String versionName) {
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        sharedPreferencesUtils.saveVersion(versionName);
    }
}
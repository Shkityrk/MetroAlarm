package com.example.samsungschoolproject.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samsungschoolproject.R;

import java.io.IOException;

public class SettingsMenuActivity extends AppCompatActivity {
    private static final int PICK_RINGTONE_REQUEST = 1;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private boolean isMusicPlaying = false;
    Uri content_uri = Uri.parse("android.resource://com.example.samsungschoolproject/" + R.raw.song);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        Button goBackButton = findViewById(R.id.back_to_main_menu);
        Button buttonSelectRingtone = findViewById(R.id.buttonSelectRingtone);  // Кнопка выбора рингтона
        seekBarVolume = findViewById(R.id.seekBarVolume);  // Ползунок громкости
        Button buttonSaveSettings = findViewById(R.id.buttonSaveSettings);  // Кнопка сохранения настроек

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
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.setVolume(progress / 100f, progress / 100f);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {  // Листенер для кнопки сохранения настроек
            @Override
            public void onClick(View v) {

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_RINGTONE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    content_uri= Uri.parse(uri.getPath());

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
//    private void saveSettings(Uri uri) {
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("ringtonePath", uri.toString());
//        editor.apply();
//
//        Log.d("ringtonePath", "saveSettings: " + sharedPreferences.getString("ringtonePath", null));
//        Toast.makeText(this, "Настройки сохранены", Toast.LENGTH_LONG).show();
//    }
//
//    private String getRingtonePath() {
//        String selectedRingtonePath;
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        selectedRingtonePath = sharedPreferences.getString("ringtonePath", null);
//        Log.d("ringtonePath", "getRingtonePath: " + selectedRingtonePath);
//        return selectedRingtonePath;
//    }

    private void setMediaPlayer(Uri uri_song) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        try {

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
}
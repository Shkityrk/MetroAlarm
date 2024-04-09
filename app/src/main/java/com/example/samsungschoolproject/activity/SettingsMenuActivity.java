package com.example.samsungschoolproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.samsungschoolproject.activity.MainActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.samsungschoolproject.R;




public class SettingsMenuActivity extends AppCompatActivity {
    private static final int PICK_AUDIO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
        Button goBackButton = (Button) findViewById(R.id.back_to_main_menu);
        Button selectRingtoneButton = findViewById(R.id.buttonSelectRingtone);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        selectRingtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открываем окно выбора файла
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent, PICK_AUDIO_REQUEST);
            }
        });


    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedAudioUri = data.getData();

            // Проверяем тип выбранного файла
            if (isAudioFile(selectedAudioUri)) {
                // Выводим сообщение об успешной операции
                Toast.makeText(this, "Файл выбран успешно", Toast.LENGTH_SHORT).show();
                // Здесь можно сохранить ссылку на выбранный аудиофайл и выполнить необходимые действия с ним
            } else {
                // Выводим сообщение о необходимости выбрать аудиофайл
                Toast.makeText(this, "Выберите аудиофайл", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Метод для проверки типа файла
    private boolean isAudioFile(Uri uri) {
        String mimeType = getContentResolver().getType(uri);
        return mimeType != null && mimeType.startsWith("audio/");
    }

}
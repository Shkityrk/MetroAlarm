package com.example.samsungschoolproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.samsungschoolproject.R;

public class IntroSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro_success);

        // Assuming 'main' is the root view, use android.R.id.content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Find the button and set OnClickListener
        findViewById(R.id.buttonOpenMainActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to start MainActivity
                Intent intent = new Intent(IntroSuccessActivity.this, MainActivity.class);
                // Close all activities in the current task
                finishAffinity();
                // Start MainActivity
                startActivity(intent);
            }
        });
    }
}
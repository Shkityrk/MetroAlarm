package com.example.samsungschoolproject.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.data.StationRepository;
import com.example.samsungschoolproject.model.Station;

import java.util.List;

public class DevActivity extends AppCompatActivity {
    private StationRepository stationRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dev);

        stationRepository = new StationRepository(getApplication());

        EditText editTextStationName = findViewById(R.id.editText_stationName);
        Button buttonDeleteStation = findViewById(R.id.button_deleteStation);

        EditText editTextAddStationName = findViewById(R.id.editText_add_stationName);
        EditText editTextLine = findViewById(R.id.editText_line);
        EditText idStation = findViewById(R.id.id_station);
        Button buttonAddStation = findViewById(R.id.button_addStation);

        buttonDeleteStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationName = editTextStationName.getText().toString();
                deleteStation(stationName);
            }
        });

        buttonAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//
                //                Log.d("DevActivity", "onClick: " + station.getName());
//
//                // Insert the station into the database
//                addStation(station);
                String a = editTextAddStationName.getText().toString();
                Station station = new Station(a, "1", "latitude", "longitude", "width_neighbour1", "longitude_neighbour1", "width_neighbour2", "longitude_neighbour2", "false", "false");
                Log.d("DevActivity", "onClick: " + station.getName());
                addStation(station);



            }
        });
    }
    private void deleteStation(String stationName) {
        stationRepository.getAllStations().observe(this, new Observer<List<Station>>() {
            @Override
            public void onChanged(List<Station> stations) {
                for (Station station : stations) {
                    if (station.getName().equalsIgnoreCase(stationName)) {
                        stationRepository.deleteStation(station);
                        Toast.makeText(DevActivity.this, "Station deleted", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(DevActivity.this, "Station not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addStation(Station station) {
        stationRepository.insert(station);
        Toast.makeText(DevActivity.this, "Station added", Toast.LENGTH_SHORT).show();
    }
}
package com.example.randomsamplingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import java.io.File;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView valenceLabel;
    TextView arousalLabel;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        Intent alarmServiceIntent = new Intent(this, AlarmService.class);
        startService(alarmServiceIntent);

        SeekBar valenceBar = findViewById(R.id.valence);
        SeekBar arousalBar = findViewById(R.id.arousal);

        int valenceProgress = valenceBar.getProgress();
        valenceLabel = findViewById(R.id.valence_label);
        valenceLabel.setText("Valence: " + valenceProgress);

        int arousalProgress = arousalBar.getProgress();
        arousalLabel = findViewById(R.id.arousal_label);
        arousalLabel.setText("Arousal: " + arousalProgress);

        valenceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valenceLabel.setText("Valence: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        arousalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                arousalLabel.setText("Arousal: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void save(View v) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Date date = Calendar.getInstance().getTime();
            String valence = valenceLabel.getText().toString();
            String arousal = arousalLabel.getText().toString();
            FileOutputStream out = null;

            try {
                out = openFileOutput(date.toString() + ".txt", Context.MODE_PRIVATE);
                out.write(valence.getBytes());
                out.write(" ".getBytes());
                out.write(arousal.getBytes());
                out.flush();
                out.close();
                Toast.makeText(this, "Done " + context.getFilesDir(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
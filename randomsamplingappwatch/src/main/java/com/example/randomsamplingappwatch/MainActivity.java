package com.example.randomsamplingappwatch;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.randomsamplingappwatch.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    TextView valenceLabel;
    TextView arousalLabel;

    private ActivityMainBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SeekBar valenceBar = findViewById(R.id.valence);
        SeekBar arousalBar = findViewById(R.id.arousal);

        int valenceProgress = valenceBar.getProgress();
        valenceLabel = findViewById(R.id.valence_label);
        valenceLabel.setText("Valence: " + valenceProgress);

        int arousalProgress = arousalBar.getProgress();
        arousalLabel = findViewById(R.id.arousal_label);
        arousalLabel.setText("Arousal: " + arousalProgress);

        valenceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
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

    public void save(View v){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            if(checkPermission()){
                File sdcard = Environment.getExternalStorageDirectory();
                File dir = new File(sdcard.getAbsolutePath() + "/Random Sampling Data/");
                dir.mkdir();
                Date date = Calendar.getInstance().getTime();
                File file = new File(dir, date.toString() + ".txt");
                FileOutputStream out = null;
                String valence = valenceLabel.getText().toString();
                String arousal = arousalLabel.getText().toString();

                try{
                    out = new FileOutputStream(file);
                    out.write(valence.getBytes());
                    out.write(" ".getBytes());
                    out.write(arousal.getBytes());
                    out.close();
                    Toast.makeText(this, "Done " + sdcard.getAbsolutePath() + "/Random Sampling Data/", Toast.LENGTH_SHORT).show();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            else{
                requestPermission();
            }
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }

        return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "Please allow this permission to create new files", Toast.LENGTH_LONG).show();
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}
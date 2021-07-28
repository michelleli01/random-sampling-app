package com.example.randomsamplingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class AlarmService extends Service {

    private final String TAG = this.getClass().getName();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "alarm: AlarmService.onStartCommand() received start id " + startId + ": " + intent);

        if (intent != null) {
            createAlarm();
        } else {
            Toast.makeText(getBaseContext(), "Intent was null in AlarmService.", Toast.LENGTH_LONG).show();
        }

        return START_STICKY;
    }

    private void createAlarm() {
        Intent notify = new Intent(this, NotificationService.class);
        notify.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, notify, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 30);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Log.d(TAG, "alarm set for " + calendar.getTime().toString());
//            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_DAY, AlarmManager.INTERVAL_HALF_DAY, notify);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

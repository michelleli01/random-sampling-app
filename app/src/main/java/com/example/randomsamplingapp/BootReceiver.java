package com.example.randomsamplingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent("com.example.randomsamplingapp.AlarmService");
        i.setClass(context, AlarmService.class);
        context.startService(i);
    }
}

package com.example.vajiraprabuddhaka.edcrs.data.control;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

/**
 * Created by Vajira Prabuddhaka on 12/20/2016.
 */

public class BackgroundService extends IntentService {

    private static String TAG = "com.example.vajiraprabuddhaka.edcrs.data.control";

    // Must create a default constructor
    public BackgroundService() {
        // Used to name the worker thread, important only for debugging.
        super("BackgroundService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "The service is running");
    }
}

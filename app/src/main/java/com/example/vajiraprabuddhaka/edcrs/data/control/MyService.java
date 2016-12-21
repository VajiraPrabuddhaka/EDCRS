package com.example.vajiraprabuddhaka.edcrs.data.control;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private static final String TAG = "com.example.vajiraprabuddhaka.edcrs.data.control";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"On Start");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i= 0; i<5; i++){
                    long futureTime = System.currentTimeMillis() + 5000;
                    while (System.currentTimeMillis()<futureTime){
                        synchronized (this){
                            try {
                                wait(futureTime - System.currentTimeMillis() );
                                if (haveNetworkConnection()) {
                                    Log.i(TAG, "Service is doing something");
                                    //should be call sync method here
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        Thread vajirasThread = new Thread(r);
        vajirasThread.start();
        return Service.START_STICKY;

    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"On Destroy");
    }
}

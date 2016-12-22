package com.example.vajiraprabuddhaka.edcrs.data.control;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vajiraprabuddhaka.edcrs.data.ui.LoginActivity;
import com.example.vajiraprabuddhaka.edcrs.data.ui.Main1Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyService extends Service {

    private DBsyncController controller = new DBsyncController(this);
    private ArrayList<HashMap<String, String>> diseasesList;
    private ArrayList<HashMap<String, String>> patientsList;
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
    public int onStartCommand(final Intent intent, int flags, int startId) {
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

                                    diseasesList = controller.getAllDiseases();
                                    patientsList = controller.getAllPatients();

                                    syncDataPost(getApplicationContext(), diseasesList, "add url ");



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
    private void getLogin(final String username, final String password) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, "http://192.168.153.1:100/EDCRS-PHP/LoginDataPost.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id", username);
                MyData.put("password",password);//Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    public void syncDataPost(Context context, final ArrayList<HashMap<String, String>> dataList, String url){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(response.substring(1).equals("Login Successful!")){

                }
                //Toast.makeText(ProLogin.this, returnValue[0], Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                String data;
                for(HashMap<String, String> map: dataList)
                    for (Map.Entry<String, String> entry : map.entrySet())
                        params.put(entry.getKey(), entry.getValue());
                return params;
            }

                /*@Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }*/
        };
        queue.add(sr);
    }
}

package com.example.vajiraprabuddhaka.edcrs.data.ui;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DetailAutoFill {
    private String[] city={"Kurunegala", "Gampaha", "A' Puraya"};
    private String[] district={"Kurunegala", "Gampaha", "A'pura"};
    private String[] ageGroup = {"1-12 months","1-5 yrs","6-12 yrs","13-20 yrs","21-60 yrs", "Above 60 yrs"};

    public String[] getCity() {
        return city;
    }

    public void setCity(String[] city) {
        this.city = city;
    }

    public String[] getDistrict() {
        return district;
    }

    public void setDistrict(String[] district) {
        this.district = district;
    }

    public void populateCities(String district, final Context context){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, "http://192.168.153.1:100/EDCRS-PHP/registerDataPost.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    String s = response;
                    city = s.split("/n");

                } else {
                    Toast.makeText(context, "No data", Toast.LENGTH_LONG).show();
                }
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        });
    }
    public void populateDistricts(final Context context){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, "http://192.168.153.1:100/EDCRS-PHP/registerDataPost.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    String s = response;
                    district = s.split("/n");

                } else {
                    Toast.makeText(context, "No data", Toast.LENGTH_LONG).show();
                }
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        });
    }
    public boolean isAlpha(String s){
        char[] chars = s.toCharArray();
        for(char c : chars){
            if(!Character.isLetter(c) ){
                return false;
            }
        }
        return true;
    }

}

package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vajiraprabuddhaka.edcrs.R;

public class Main1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Intent myIntent = new Intent(Main1Activity.this, MainActivity.class);

        //here we have to check whether logged or not; if logged start report activity else start reporting activity
        Main1Activity.this.startActivity(myIntent);
    }
}

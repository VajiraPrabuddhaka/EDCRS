package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vajiraprabuddhaka.edcrs.R;
import com.example.vajiraprabuddhaka.edcrs.data.control.SaveSharedPreference;

public class Main1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        final Intent myIntent = new Intent(Main1Activity.this, MainActivity.class);

        if(SaveSharedPreference.getUserName(Main1Activity.this).length() == 0)
        {
            // call Login Activity
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please login first")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            Main1Activity.this.startActivity(myIntent);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            // Stay at the current activity.
        }

        //here we have to check whether logged or not; if logged start report activity else start reporting activity

    }
}

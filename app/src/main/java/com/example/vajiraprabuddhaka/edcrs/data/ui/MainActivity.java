package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vajiraprabuddhaka.edcrs.R;
import com.example.vajiraprabuddhaka.edcrs.data.control.BackgroundService;
import com.example.vajiraprabuddhaka.edcrs.data.control.MyService;
import com.example.vajiraprabuddhaka.edcrs.data.control.SaveSharedPreference;


public class MainActivity extends AppCompatActivity {
    Button search;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        search = (Button) findViewById(R.id.diseaseSearch);
         login = (Button) findViewById(R.id.login);





        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
                {
                    // call Login Activity
                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please login first")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent2);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    */
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Yor are not logged in. Please login to continue!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent2);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    // Stay at the current activity.
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("You are already logged in")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    Intent intent = new Intent(MainActivity.this, Main1Activity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });


        Intent inte = new Intent(this, MyService.class);
        startService(inte);



    }
}

package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vajiraprabuddhaka.edcrs.R;
import com.example.vajiraprabuddhaka.edcrs.data.control.SaveSharedPreference;

public class LoginActivity extends AppCompatActivity {

    Button login;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()) {
                    SaveSharedPreference.setUserName(LoginActivity.this, "vajira");
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private boolean login(){
        //this method should return if the login successfull else return false
        return true;
    }

    private boolean signup(){
        //for signup people should have internet connection
        //this method should return true if the signup successfull
        return true;
    }
}

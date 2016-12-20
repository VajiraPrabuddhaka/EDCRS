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

        signUp = (Button)findViewById(R.id.signup) ;
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });


    }

    private boolean login(){
        //this method should return true if the login successful else return false
        return true;
    }

    private boolean signup(){
        //for signup people should have internet connection
        //this method should return true if the signup successful
        return true;
    }
}

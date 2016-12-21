package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vajiraprabuddhaka.edcrs.R;
import com.example.vajiraprabuddhaka.edcrs.data.control.Config;
import com.example.vajiraprabuddhaka.edcrs.data.control.SaveSharedPreference;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    Button signUp;

    private EditText username;
    private EditText password;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login(v)) {
                    SaveSharedPreference.setUserName(LoginActivity.this, "vajira");
                    Intent myintent = new Intent(LoginActivity.this, Main1Activity.class);
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

    private boolean login(View v){
        //this method should return true if the login successful else return false
        String userName = username.getText().toString().trim();
        String passWord = password.getText().toString().trim();
        switch (v.getId()) {
            case R.id.login:



                if (userName.isEmpty()) {
                    username.setError("Please enter username");
                    return false;

                }

                if (passWord.isEmpty()) {
                    password.setError("Please enter password");
                    return false;
                }

                //cleanUp();
                break;
        }
        if (getLoginData(userName,passWord).equals("Successful")){
            return true;
        }
        else{
            return false;
        }
    }

    private String getLoginData(String username, String password) {
        //please make sure that username and password are not empty i.e ""

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        String url = "proper url should be put here";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return null;
    }

    private boolean signup(){
        //for signup people should have internet connection
        //this method should return true if the signup successful
        return true;
    }
}

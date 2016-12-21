package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vajiraprabuddhaka.edcrs.R;
import com.example.vajiraprabuddhaka.edcrs.data.control.Config;
import com.example.vajiraprabuddhaka.edcrs.data.control.SaveSharedPreference;

import java.util.HashMap;
import java.util.Map;

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

                login(v);


            }
        });

        signUp = (Button)findViewById(R.id.signup) ;
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private void login(View v){
        //this method should return true if the login successful else return false
        String userName = username.getText().toString().trim();
        String passWord = password.getText().toString().trim();




                if (userName.isEmpty()) {
                    username.setError("Please enter username");
                    Toast.makeText(LoginActivity.this, "Start", Toast.LENGTH_SHORT);

                }

                if (passWord.isEmpty()) {
                    password.setError("Please enter password");

                }
                if(!userName.isEmpty() && !passWord.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Start", Toast.LENGTH_SHORT);
                    getLogin(userName,passWord);
                }




    }

    private void getLogin(final String username, final String password) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        Toast.makeText(LoginActivity.this, "Connection1", Toast.LENGTH_SHORT);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, "http://192.168.153.1:100/EDCRS-PHP/LoginDataPost.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                if (response.equals("Login Successful!")) {
                    Toast.makeText(LoginActivity.this, "correct Password", Toast.LENGTH_LONG).show();
                    SaveSharedPreference.setUserName(LoginActivity.this, username);
                    Intent myintent = new Intent(LoginActivity.this, Main1Activity.class);
                    startActivity(myintent);

                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_LONG).show();

                }
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Connection Problem", Toast.LENGTH_SHORT);
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

    private boolean signup(){
        //for signup people should have internet connection
        //this method should return true if the signup successful
        return true;
    }
}

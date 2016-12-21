package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vajiraprabuddhaka.edcrs.R;
import com.example.vajiraprabuddhaka.edcrs.data.control.SaveSharedPreference;
import com.example.vajiraprabuddhaka.edcrs.data.model.Doctor;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText fname;
    private EditText lname;
    private EditText email;
    private EditText phone;
    private EditText docID;
    private EditText officerID;
    private EditText nationalID;
    private EditText password1;
    private EditText password2;

    private CheckBox doctor;
    private CheckBox healthOfficer;

    private Button signUp;
    String name;
    String checkMail;
    String id;
    String nId;
    String pass;
    String number;
    String conf;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        docID = (EditText) findViewById(R.id.docID);
        docID.setEnabled(false);
        officerID = (EditText) findViewById(R.id.officerID);
        officerID.setEnabled(false);
        nationalID = (EditText) findViewById(R.id.nationalID);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);

        doctor = (CheckBox) findViewById(R.id.doctor);
        healthOfficer = (CheckBox) findViewById(R.id.hOfficer);

        signUp = (Button) findViewById(R.id.signIn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMail = email.getText().toString();
                name = fname.getText().toString().trim() + "" + lname.getText().toString().trim();
                nId = nationalID.getText().toString();
                pass = password1.getText().toString();
                conf = password2.getText().toString();
                number = phone.getText().toString();

                if (isAlpha(name) && (!fname.getText().toString().isEmpty() || !lname.getText().toString().isEmpty())) {
                    if (!checkMail.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(checkMail).matches()) {
                        if (pass.equals(conf)) {
                            if (officerID.getText() != docID.getText() && !nationalID.getText().toString().isEmpty()) {
                                if (doctor.isChecked()) {
                                    registerOfficer(fname.getText().toString().trim(),
                                            lname.getText().toString().trim(),
                                            "1",
                                            docID.getText().toString().trim(),
                                            nationalID.getText().toString().trim(),
                                            email.getText().toString().trim(),
                                            phone.getText().toString().trim(),
                                            password1.getText().toString().trim());
                                }

                                else {
                                    registerOfficer(fname.getText().toString().trim(),
                                            lname.getText().toString().trim(),
                                            "2",
                                            officerID.getText().toString().trim(),
                                            nationalID.getText().toString().trim(),
                                            email.getText().toString().trim(),
                                            phone.getText().toString().trim(),
                                            password1.getText().toString().trim());
                                }

                            } else {
                                if(officerID.isEnabled()) officerID.setError("Please enter Officer Registration ID");
                                else docID.setError("Please enter Doctor Registration ID");
                            }
                        } else {
                            password1.getText().clear();
                            password2.getText().clear();
                            password1.setError("Passwords does not match");
                            password2.setError("Passwords does not match");
                        }
                    } else {
                        email.getText().clear();
                        email.setError("Enter a valid email address");
                    }
                } else {
                    fname.getText().clear();
                    lname.getText().clear();
                    fname.setError("Invalid name! Check again");
                    lname.setError("Check again");
                }
            }
        });
        doctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (doctor.isChecked()) {
                    healthOfficer.setEnabled(false);
                    docID.setEnabled(true);
                    officerID.getText().clear();
                } else {
                    healthOfficer.setEnabled(true);
                    docID.setEnabled(false);
                    docID.getText().clear();
                }
            }
        });

        healthOfficer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (healthOfficer.isChecked()) {
                    doctor.setEnabled(false);
                    officerID.setEnabled(true);
                    docID.getText().clear();
                } else {
                    doctor.setEnabled(true);
                    officerID.setEnabled(false);
                    officerID.getText().clear();
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean isAlpha(String s) {
        char[] chars = s.toCharArray();
        int x = 0;
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public void registerOfficer(final String fname, final String lname,final String officer_type, final String officer_id, final String nic, final String email, final String mobile, final String pwd) {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, "http://192.168.153.1:100/EDCRS-PHP/registerDataPost.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                if (response.equals("Registration Successful!")) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered! please login now", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "connection problem", Toast.LENGTH_LONG).show();


                }
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Connection Problem", Toast.LENGTH_SHORT);
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("full_name", fname+" "+lname);
                MyData.put("id", officer_id);
                MyData.put("national_id", nic);
                MyData.put("email", email);
                MyData.put("phone", mobile);
                MyData.put("password", pwd);
                MyData.put("type", officer_type);//Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

}

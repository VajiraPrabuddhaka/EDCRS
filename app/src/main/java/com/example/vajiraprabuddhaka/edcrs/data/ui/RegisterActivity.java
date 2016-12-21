package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vajiraprabuddhaka.edcrs.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        docID = (EditText) findViewById(R.id.docID); docID.setEnabled(false);
        officerID = (EditText) findViewById(R.id.officerID); officerID.setEnabled(false);
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
                name = fname.getText().toString() + " " + lname.getText().toString();
                nId = nationalID.getText().toString();
                pass = password1.getText().toString();
                number = phone.getText().toString();

                if(isAlpha(name)){
                    if(!checkMail.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(checkMail).matches()){
                        if(password1.getText() == password2.getText()){
                            if(officerID.getText() != docID.getText() && !nationalID.getText().toString().isEmpty()){
                                if(doctor.isChecked()) id = docID.getText().toString();
                                else id = officerID.getText().toString();
                                //controller.addOfficer or addDoc
                                //controller.sendData
                                Intent intent = new Intent(RegisterActivity.this, Main1Activity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Enter ID", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            password1.getText().clear();
                            password2.getText().clear();
                            password1.setHintTextColor(Color.RED);
                            password2.setHintTextColor(Color.RED);
                            Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        email.getText().clear();
                        email.setHintTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "Enter a Valid Email", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    fname.setHintTextColor(Color.RED);
                    lname.setHintTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        doctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(doctor.isChecked()) {
                    healthOfficer.setEnabled(false); docID.setEnabled(true); officerID.getText().clear();
                }
                else {
                    healthOfficer.setEnabled(true); docID.setEnabled(false); docID.getText().clear();
                }
            }
        });

        healthOfficer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(healthOfficer.isChecked()) {
                    doctor.setEnabled(false); officerID.setEnabled(true); docID.getText().clear();
                }
                else {
                    doctor.setEnabled(true); officerID.setEnabled(false); officerID.getText().clear();
                }
            }
        });


    }
    public boolean isAlpha(String s){
        char[] chars = s.toCharArray();
        int x=0;
        for(char c : chars){
            if(!Character.isLetter(c) || !Character.isSpaceChar(c)){
                return false;
            }
            if(Character.isLetter(c)) x = 1;
        }

        if(x>0) return true;
        return false;
    }
}

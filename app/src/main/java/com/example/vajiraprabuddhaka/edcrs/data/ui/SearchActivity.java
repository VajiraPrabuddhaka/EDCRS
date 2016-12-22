package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.vajiraprabuddhaka.edcrs.R;
import com.example.vajiraprabuddhaka.edcrs.data.control.Config;
import com.example.vajiraprabuddhaka.edcrs.data.control.DBsyncController;

public class SearchActivity extends AppCompatActivity {
    Button diseaseContinue;
    EditText diseaseName;
    AutoCompleteTextView district;
    AutoCompleteTextView city;
    String[] districts;
    String[] cities;

    DetailAutoFill autoFill;

    DBsyncController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        diseaseName = (EditText) findViewById(R.id.diseaseName);
        diseaseContinue = (Button) findViewById(R.id.diseaseContinue);
        district = (AutoCompleteTextView) findViewById(R.id.district);
        city = (AutoCompleteTextView) findViewById(R.id.city);

        autoFill.populateDistricts(getApplicationContext());
        districts = autoFill.getDistrict();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (SearchActivity.this.getApplicationContext(), android.R.layout.simple_dropdown_item_1line, districts);
        district.setAdapter(adapter1);
        district.setThreshold(0);


        diseaseContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String name = diseaseName.getText().toString().trim();
                String currentDistrict = district.getText().toString().trim();
                String currentCity = city.getText().toString().trim();
                if(isAlpha(name) && !diseaseName.getText().toString().isEmpty()){
                    if(isAlpha(currentDistrict) && !district.getText().toString().isEmpty()){
                        if(isAlpha(currentCity) && !city.getText().toString().isEmpty()){

                        }
                    }
                }
                else diseaseName.setError("Wrong Disease Name");
            }
        });

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
}

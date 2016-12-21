package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vajiraprabuddhaka.edcrs.R;

import java.util.Arrays;

public class ReportActivity extends AppCompatActivity {
    private String[] districts;
    private String[] cities;
    private String[] types;
    private String[] diseases;

    private String currentType;
    private String currentDisease;
    private String currentDistrict;
    private String currentCity;

    private DiseaseAutoFill diseaseAutoFill;
    private DetailAutoFill detailAutoFill;

    private AutoCompleteTextView diseaseType;
    private AutoCompleteTextView diseaseName;
    private EditText patientName;
    private AutoCompleteTextView district;
    private AutoCompleteTextView city;
    private EditText age;

    private CheckBox male;
    private CheckBox female;

    private Button addPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        diseaseName = (AutoCompleteTextView) findViewById(R.id.diseaseName);
        diseaseType = (AutoCompleteTextView) findViewById(R.id.diseaseType);
        patientName = (EditText) findViewById(R.id.fname);
        district = (AutoCompleteTextView) findViewById(R.id.district);
        city = (AutoCompleteTextView) findViewById(R.id.city);
        age = (EditText) findViewById(R.id.age);

        diseaseAutoFill = new DiseaseAutoFill();
        diseaseAutoFill.populateTypes();

        types = diseaseAutoFill.getTypes();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, types);
        diseaseType.setAdapter(adapter1);
        diseaseType.setThreshold(1);

        diseaseName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentType = diseaseType.getText().toString();
                if(currentType.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter a disease type", Toast.LENGTH_SHORT);
                    diseaseType.setHintTextColor(Color.RED);
                }

            }
        });

        diseaseAutoFill.populateDiseases();
        diseases = diseaseAutoFill.getDiseases();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, diseases);
        diseaseName.setAdapter(adapter2);
        diseaseName.setThreshold(1);

        detailAutoFill = new DetailAutoFill();
        detailAutoFill.populateDistricts();
        districts = detailAutoFill.getDistrict();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, districts);
        district.setAdapter(adapter3);
        district.setThreshold(0);

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentDistrict = district.getText().toString();
                if(currentDistrict.equals("") || Arrays.asList(districts).contains(currentDistrict)){
                    Toast.makeText(getApplicationContext(), "Please enter a valid District", Toast.LENGTH_SHORT);
                    district.getText().clear();
                    district.setHintTextColor(Color.RED);
                }

            }
        });

        detailAutoFill.populateCities(currentDistrict);
        cities = detailAutoFill.getCity();

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, cities);
        city.setAdapter(adapter4);
        city.setThreshold(0);

        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(male.isChecked()) female.setEnabled(false);
                else female.setEnabled(true);
            }
        });

        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(female.isChecked()) male.setEnabled(false);
                else male.setEnabled(true);
            }
        });

        int ageInt;

        addPatient = (Button) findViewById(R.id.addPatient);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDisease = diseaseName.getText().toString();
                currentType = diseaseType.getText().toString();
                currentDistrict = district.getText().toString();
                currentCity = city.getText().toString();
                try {
                    Integer.getInteger(age.getText().toString());
                }
                catch (Exception e) {Toast.makeText(getApplicationContext(), "Invalid Age. Only use numbers", Toast.LENGTH_SHORT).show();
                    age.getText().clear();
                    age.setHintTextColor(Color.RED);
                    age.setHint("Age (Eg: 22)");
                    return;}

                if((currentDisease.equals("") || currentType.equals("")) && Integer.getInteger(age.getText().toString())<110){
                    Toast.makeText(getApplicationContext(), "Please enter a Disease and Type of disease", Toast.LENGTH_LONG).show();
                    diseaseName.setHintTextColor(Color.RED);
                    diseaseType.setHintTextColor(Color.RED);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data Successful", Toast.LENGTH_SHORT).show();
                    //send to sql
                    Intent intent = new Intent(ReportActivity.this, Main1Activity.class);
                    startActivity(intent);
                }
            }
        });

    }
}

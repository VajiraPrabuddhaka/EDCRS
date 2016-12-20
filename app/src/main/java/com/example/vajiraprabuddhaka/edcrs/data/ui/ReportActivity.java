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
import android.widget.Toast;

import com.example.vajiraprabuddhaka.edcrs.R;

public class ReportActivity extends AppCompatActivity {
    private String[] nameID;
    private String[] types;
    private String[] diseases;

    private String currentType;
    private String currentDisease;

    private DiseaseAutoFill diseaseAutoFill;
    private NameAutoFill nameAutoFill;

    private AutoCompleteTextView diseaseType;
    private AutoCompleteTextView diseaseName;
    private AutoCompleteTextView patientName;

    private Button addPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        diseaseName = (AutoCompleteTextView) findViewById(R.id.diseaseName);
        diseaseType = (AutoCompleteTextView) findViewById(R.id.diseaseType);
        patientName = (AutoCompleteTextView) findViewById(R.id.patientName);

        diseaseAutoFill = new DiseaseAutoFill();
        diseaseAutoFill.populateTypes();

        types = diseaseAutoFill.getTypes();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, types);
        diseaseType.setAdapter(adapter1);
        diseaseType.setThreshold(3);

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

        diseaseAutoFill.populateDiseases(currentDisease);
        diseases = diseaseAutoFill.getDiseases();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, diseases);
        diseaseName.setAdapter(adapter2);
        diseaseName.setThreshold(3);

        nameAutoFill = new NameAutoFill();
        nameAutoFill.populateData();
        nameID = nameAutoFill.getNameID();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, nameID);
        patientName.setAdapter(adapter3);
        patientName.setThreshold(3);

        addPatient = (Button) findViewById(R.id.addPatient);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDisease = diseaseName.getText().toString();
                currentType = diseaseType.getText().toString();

                if(currentDisease.equals("") || currentType.equals("")){
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

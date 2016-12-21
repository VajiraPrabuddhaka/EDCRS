package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPatient.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPatient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPatient extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    private OnFragmentInteractionListener mListener;

    public AddPatient() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddPatient newInstance(String param1, String param2) {
        AddPatient fragment = new AddPatient();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_patient, container, false);

        diseaseName = (AutoCompleteTextView) view.findViewById(R.id.diseaseName);
        diseaseType = (AutoCompleteTextView) view.findViewById(R.id.diseaseType);
        patientName = (EditText) view.findViewById(R.id.fname);
        district = (AutoCompleteTextView) view.findViewById(R.id.district);
        city = (AutoCompleteTextView) view.findViewById(R.id.city);
        age = (EditText) view.findViewById(R.id.age);

        diseaseAutoFill = new DiseaseAutoFill();
        diseaseAutoFill.populateTypes();

        types = diseaseAutoFill.getTypes();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (AddPatient.this.getActivity(), android.R.layout.simple_dropdown_item_1line, types);
        diseaseType.setAdapter(adapter1);
        diseaseType.setThreshold(1);

        diseaseName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentType = diseaseType.getText().toString();
                if(currentType.equals("")){
                    Toast.makeText(AddPatient.this.getActivity(), "Please enter a disease type", Toast.LENGTH_SHORT);
                    diseaseType.setHintTextColor(Color.RED);
                }

            }
        });

        diseaseAutoFill.populateDiseases(currentDisease);
        diseases = diseaseAutoFill.getDiseases();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (AddPatient.this.getActivity(), android.R.layout.simple_dropdown_item_1line, diseases);
        diseaseName.setAdapter(adapter2);
        diseaseName.setThreshold(1);

        detailAutoFill = new DetailAutoFill();
        detailAutoFill.populateDistricts();
        districts = detailAutoFill.getDistrict();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (AddPatient.this.getActivity(), android.R.layout.simple_dropdown_item_1line, districts);
        district.setAdapter(adapter3);
        district.setThreshold(0);

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentDistrict = district.getText().toString();
                if(currentDistrict.equals("") || Arrays.asList(districts).contains(currentDistrict)){
                    Toast.makeText(AddPatient.this.getActivity(), "Please enter a valid District", Toast.LENGTH_SHORT);
                    district.getText().clear();
                    district.setHintTextColor(Color.RED);
                }

            }
        });

        detailAutoFill.populateCities(currentDistrict);
        cities = detailAutoFill.getCity();

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>
                (AddPatient.this.getActivity(), android.R.layout.simple_dropdown_item_1line, cities);
        city.setAdapter(adapter4);
        city.setThreshold(0);

        male = (CheckBox) view.findViewById(R.id.male);
        female = (CheckBox) view.findViewById(R.id.female);

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

        addPatient = (Button) view.findViewById(R.id.addPatient);
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
                catch (Exception e) {Toast.makeText(AddPatient.this.getActivity(), "Invalid Age. Only use numbers", Toast.LENGTH_SHORT).show();
                    age.getText().clear();
                    age.setHintTextColor(Color.RED);
                    age.setHint("Age (Eg: 22)");
                    return;}

                if((currentDisease.equals("") || currentType.equals("")) && Integer.getInteger(age.getText().toString())<110){
                    Toast.makeText(AddPatient.this.getActivity(), "Please enter a Disease and Type of disease", Toast.LENGTH_LONG).show();
                    diseaseName.setHintTextColor(Color.RED);
                    diseaseType.setHintTextColor(Color.RED);
                }
                else{
                    Toast.makeText(AddPatient.this.getActivity(), "Data Successful", Toast.LENGTH_SHORT).show();
                    //send to sql
                    Intent intent = new Intent(AddPatient.this.getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

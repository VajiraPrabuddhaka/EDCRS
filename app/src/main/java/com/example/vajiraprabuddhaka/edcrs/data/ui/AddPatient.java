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
import com.example.vajiraprabuddhaka.edcrs.data.control.DBsyncController;

import java.util.Arrays;
import java.util.HashMap;

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
    private String[] diseases;

    private String currentDisease;
    private String currentDistrict;
    private String currentCity;
    private String currentID;
    private String currentAge;
    private String currentName;
    private String details;

    private DiseaseAutoFill diseaseAutoFill;
    private DetailAutoFill detailAutoFill;

    private AutoCompleteTextView diseaseName;
    private EditText patientName;
    private AutoCompleteTextView district;
    private AutoCompleteTextView city;
    private EditText age;
    private EditText nID;
    private EditText moreDetails;

    private CheckBox male;
    private CheckBox female;

    private Button addPatient;

    private OnFragmentInteractionListener mListener;

    private DBsyncController dbController;

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
    HashMap<String, String> diseaseList = new HashMap<String, String>();
    HashMap<String, String> patientsList = new HashMap<String, String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_patient, container, false);
        dbController = new DBsyncController(view.getContext());

        diseaseName = (AutoCompleteTextView) view.findViewById(R.id.diseaseName);
        patientName = (EditText) view.findViewById(R.id.fname);
        district = (AutoCompleteTextView) view.findViewById(R.id.district);
        city = (AutoCompleteTextView) view.findViewById(R.id.city);
        age = (EditText) view.findViewById(R.id.age);
        nID = (EditText) view.findViewById(R.id.patientID);
        moreDetails = (EditText) view.findViewById(R.id.details);

        diseaseAutoFill = new DiseaseAutoFill();
        diseaseAutoFill.populateTypes();

        diseaseAutoFill.populateDiseases();
        diseases = diseaseAutoFill.getDiseases();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (AddPatient.this.getActivity(), android.R.layout.simple_dropdown_item_1line, diseases);
        diseaseName.setAdapter(adapter2);
        diseaseName.setThreshold(1);

        detailAutoFill = new DetailAutoFill();
        detailAutoFill.populateDistricts(getContext());
        districts = detailAutoFill.getDistrict();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (AddPatient.this.getActivity(), android.R.layout.simple_dropdown_item_1line, districts);
        district.setAdapter(adapter3);
        district.setThreshold(0);

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentDistrict = district.getText().toString().trim();
                if(currentDistrict.isEmpty() || !Arrays.asList(districts).contains(currentDistrict)){
                    Toast.makeText(AddPatient.this.getActivity(), "Please enter a valid District", Toast.LENGTH_SHORT).show();
                    district.getText().clear();
                    city.getText().clear();
                    district.setHintTextColor(Color.RED);
                }

            }
        });

        detailAutoFill.populateCities(currentDistrict, getContext());
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


        addPatient = (Button) view.findViewById(R.id.addPatient);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDisease = diseaseName.getText().toString().trim();
                currentDistrict = district.getText().toString().trim();
                currentCity = city.getText().toString().trim();
                currentID = nID.getText().toString();
                currentAge = age.getText().toString();
                currentName = patientName.getText().toString().trim();
                details = moreDetails.getText().toString().trim();
                if(detailAutoFill.isAlpha(currentDisease) && !diseaseName.getText().toString().isEmpty()){
                    if(detailAutoFill.isAlpha(currentCity) && !city.getText().toString().isEmpty()){
                        if(detailAutoFill.isAlpha(currentDistrict) && !district.getText().toString().isEmpty() && Arrays.asList(districts).contains(currentDistrict)){
                            if(detailAutoFill.isAlpha(currentName) && !patientName.getText().toString().isEmpty()
                                    && !currentAge.isEmpty() && !currentID.isEmpty() && currentID.length()>8){
                                HashMap<String, String> diseaseList = new HashMap<String, String>();
                                HashMap<String, String> patientsList = new HashMap<String, String>();
                                dbController.insertDiseases(diseaseList, currentDisease, details, "");
                                dbController.insertPatients(patientsList, currentName, currentAge, currentID, currentCity, currentDistrict);
                                district.getText().clear();
                                city.getText().clear();
                                diseaseName.getText().clear();
                                patientName.getText().clear();
                                age.getText().clear();
                                nID.getText().clear();
                                moreDetails.getText().clear();
                                Toast.makeText(AddPatient.this.getActivity(), "Disease successfully added", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(currentID.length()<9) {nID.setError("Enter 9 digit NID");}
                                else if(currentAge.isEmpty()){age.setError("Enter Age");}
                                else{patientName.setError("Invalid Name");}
                            }
                        }
                        else{
                            district.getText().clear();
                            district.setError("Please select a correct District from Dropdown menu");
                        }
                    }
                    else {
                        city.getText().clear();
                        city.setError("Please enter a correct City name");
                    }
                }
                else {
                    diseaseName.getText().clear();
                    diseaseName.setError("Please enter a correct Disease name");
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
        if (context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        } else{
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

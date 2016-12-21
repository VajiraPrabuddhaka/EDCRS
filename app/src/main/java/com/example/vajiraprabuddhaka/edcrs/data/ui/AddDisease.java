package com.example.vajiraprabuddhaka.edcrs.data.ui;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.vajiraprabuddhaka.edcrs.R;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddDisease.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddDisease#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDisease extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AutoCompleteTextView diseaseName;
    private AutoCompleteTextView district;
    private AutoCompleteTextView city;
    private Button addDisease;

    private String[] diseases;
    private String[] districts;
    private String[] cities;
    private String currentDistrict;
    private String currentCity;
    private String currentDisease;

    private DiseaseAutoFill diseaseAutoFill;
    private DetailAutoFill detailAutoFill;

    private OnFragmentInteractionListener mListener;

    public AddDisease() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDisease.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDisease newInstance(String param1, String param2) {
        AddDisease fragment = new AddDisease();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_disease, container, false);

        diseaseName = (AutoCompleteTextView) view.findViewById(R.id.diseaseName);
        district = (AutoCompleteTextView) view.findViewById(R.id.district);
        city = (AutoCompleteTextView) view.findViewById(R.id.city);

        diseaseAutoFill = new DiseaseAutoFill();
        diseaseAutoFill.populateDiseases();
        diseases = diseaseAutoFill.getDiseases();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (AddDisease.this.getActivity(), android.R.layout.simple_dropdown_item_1line, diseases);
        diseaseName.setAdapter(adapter1);
        diseaseName.setThreshold(1);

        detailAutoFill = new DetailAutoFill();
        detailAutoFill.populateDistricts();
        districts = detailAutoFill.getDistrict();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (AddDisease.this.getActivity(), android.R.layout.simple_dropdown_item_1line, districts);
        district.setAdapter(adapter2);
        district.setThreshold(1);

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentDistrict = district.getText().toString().trim();
                if(currentDistrict.isEmpty() && !Arrays.asList(districts).contains(currentDistrict)){
                    Toast.makeText(AddDisease.this.getActivity(), "Please enter a valid District", Toast.LENGTH_SHORT).show();
                    district.getText().clear();
                    city.getText().clear();
                    district.setHintTextColor(Color.RED);
                }

            }
        });
        detailAutoFill.populateCities(currentDistrict);
        cities = detailAutoFill.getCity();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (AddDisease.this.getActivity(), android.R.layout.simple_dropdown_item_1line, cities);
        city.setAdapter(adapter3);
        city.setThreshold(0);

        addDisease = (Button) view.findViewById(R.id.addDisease);
        addDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDistrict = district.getText().toString().trim();
                currentCity = city.getText().toString().trim();
                currentDisease = diseaseName.getText().toString().trim();

                if(detailAutoFill.isAlpha(currentDisease)){
                    if(detailAutoFill.isAlpha(currentCity)){
                        if(detailAutoFill.isAlpha(currentDistrict)){
                            //send to sql
                            district.getText().clear();
                            city.getText().clear();
                            diseaseName.getText().clear();
                            Toast.makeText(AddDisease.this.getActivity(), "Disease successfully added", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            district.getText().clear();
                            district.setHintTextColor(Color.RED);
                            Toast.makeText(AddDisease.this.getActivity(), "Please enter a correct District name from the dropdown list", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        city.getText().clear();
                        city.setHintTextColor(Color.RED);
                        Toast.makeText(AddDisease.this.getActivity(), "Please enter a correct City Name", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    diseaseName.getText().clear();
                    diseaseName.setHintTextColor(Color.RED);
                    Toast.makeText(AddDisease.this.getActivity(), "Please enter a correct Disease name", Toast.LENGTH_SHORT).show();
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
        if  (context instanceof OnFragmentInteractionListener) {
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

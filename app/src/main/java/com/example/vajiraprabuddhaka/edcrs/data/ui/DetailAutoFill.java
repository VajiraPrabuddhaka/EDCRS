package com.example.vajiraprabuddhaka.edcrs.data.ui;


import android.app.Application;
import android.widget.Toast;

public class DetailAutoFill {
    private String[] city={"Kurunegala", "Gampaha", "A' Puraya"};
    private String[] district={"Kurunegala", "Gampaha", "A'pura"};
    private String[] ageGroup = {"1-12 months","1-5 yrs","6-12 yrs","13-20 yrs","21-60 yrs", "Above 60 yrs"};

    public String[] getCity() {
        return city;
    }

    public void setCity(String[] city) {
        this.city = city;
    }

    public String[] getDistrict() {
        return district;
    }

    public void setDistrict(String[] district) {
        this.district = district;
    }

    public void populateCities(String district){
        //my sql

    }
    public void populateDistricts(){
        //get from sql
    }
    public boolean isAlpha(String s){
        char[] chars = s.toCharArray();
        for(char c : chars){
            if(!Character.isLetter(c) ){
                return false;
            }
        }
        return true;
    }

}

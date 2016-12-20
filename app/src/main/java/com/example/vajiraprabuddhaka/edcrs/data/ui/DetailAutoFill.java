package com.example.vajiraprabuddhaka.edcrs.data.ui;


public class DetailAutoFill {
    private String[] city;
    private String[] district;
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

}

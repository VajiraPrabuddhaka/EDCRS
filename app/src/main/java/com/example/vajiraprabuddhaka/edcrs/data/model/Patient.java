package com.example.vajiraprabuddhaka.edcrs.data.model;

import java.util.Date;

//import User;

/**
 * Created by root on 12/3/16.
 */

public class Patient extends User {
    private Date dob;
    private Epi_Disease disease;

    public Patient(String f_name, String l_name, String username, String password) {
        super(f_name, l_name, username, password);
    }

    @Override
    public void setL_Name(String l_name) {
        super.setL_Name(l_name);
    }
    @Override
    public void setF_name(String f_name) {
        super.setF_name(f_name);
    }
    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }
    @Override
    public void setAge(int age) {
        super.setAge(age);
    }
    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }
    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getF_name() {
        return super.getF_name();
    }
    @Override
    public String getL_name() {
        return super.getL_name();
    }
    @Override
    public int getAge() {
        return super.getAge();
    }
    @Override
    public String getAddress() {
        return super.getAddress();
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    public Date getDob() {
        return dob;
    }
    public Epi_Disease getDisease() {
        return disease;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public void setDisease(Epi_Disease disease) {
        this.disease = disease;
    }

}

package com.example.vajiraprabuddhaka.edcrs.data.model;

//import User;

/**
 * Created by root on 12/3/16.
 */

public class Doctor extends User {
    private String dept;
    private String status;         //like family_doc up to physician

    public Doctor(String f_name, String l_name, String username, String password) {
        super(f_name, l_name, username, password);
    }

    public String getDept() {
        return dept;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public void setAddress(String address){
        super.setAddress(address);
    }
    @Override
    public void setAge(int age){
        super.setAge(age);
    }
    @Override
    public void setF_name(String f_name) {
        super.setF_name(f_name);
    }
    @Override
    public void setL_Name(String l_name) {
        super.setL_Name(l_name);
    }
    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }
    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getL_name(){
        return super.getL_name();
    }
    @Override
    public String getF_name() { return super.getF_name(); }
    @Override
    public String getAddress(){
        return super.getAddress();
    }
    @Override
    public int getAge(){
        return super.getAge();
    }
    @Override
    public String getUsername() {
        return super.getUsername();
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}

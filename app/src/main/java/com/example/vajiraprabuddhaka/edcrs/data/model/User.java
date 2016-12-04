package com.example.vajiraprabuddhaka.edcrs.data.model;

/**
 * Created by root on 12/3/16.
 */

public class User {
    private String f_name;
    private String l_name;
    private int age;
    private String address;
    private String username;
    private String password;

    public User(String f_name, String l_name, String username, String password){
        this.f_name=f_name;
        this.l_name=l_name;
        this.username=username;
        this.password=password;
    }

    public String getL_name() {
        return l_name;
    }
    public String getF_name() {
        return f_name;
    }
    public int getAge() {
        return age;
    }
    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setL_Name(String l_name) {
        this.l_name = l_name;
    }
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setAddress(String address) {
        this.address = address;
    }



}

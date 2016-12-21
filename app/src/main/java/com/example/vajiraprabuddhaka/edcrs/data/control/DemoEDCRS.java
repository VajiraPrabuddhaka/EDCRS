package com.example.vajiraprabuddhaka.edcrs.data.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vajira Prabuddhaka on 12/15/2016.
 */

public class DemoEDCRS extends EDCRS {
    private Context context;
    private SQLiteDatabase mydatabase;


    public DemoEDCRS(Context context){
        this.context = context;
        setup();
    }

    private SQLiteDatabase getMydatabase(){
        return this.mydatabase;
    }

    @Override
    public void setup() {
        //create Database
        mydatabase = context.openOrCreateDatabase("EDCRS", context.MODE_PRIVATE, null);

        //create Epidemic disease table
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS epidemic_desease(" +
                "desease_id VARCHAR PRIMARY KEY," +
                "desease_name VARCHAR," +
                "reported_date DATE," +
                "doc_id VARCHAR" +
                " );");

        //create database age_group
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS age_group(" +
                "age_range_id VARCHAR PRIMARY KEY," +
                "age_range VARCHAR" +
                " );");

        //create database patient
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS patient(" +
                "pat_id VARCHAR PRIMARY KEY," +
                "nic VARCHAR," +
                "age INTEGER" +
                " );");

        //create city table
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS city(" +
                "postal_code VARCHAR PRIMARY KEY," +
                "city_name VARCHAR," +
                "dis_name VARCHAR,"+
                "FOREIGN KEY(dis_name) REFERENCES district(dis_name)"+
                " );");

        //create table doctor
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS doctor(" +
                "doc_id VARCHAR PRIMARY KEY," +
                "password VARCHAR," +
                "access_level VARCHAR," +
                "full_name VARCHAR" +
                "age INTEGER" +
                " );");

        //create table health officer
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS health_officer(" +
                "id VARCHAR PRIMARY KEY," +
                "nic VARCHAR," +
                "name VARCHAR," +
                "age INTEGER" +
                " );");

        //create table province
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS province(" +
                "province_id VARCHAR PRIMARY KEY," +
                "pro_name VARCHAR" +
                " );");

        //create table district
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS district(" +
                "district_id VARCHAR PRIMARY KEY," +
                "dis_name VARCHAR," +
                "pro_name VARCHAR," +
                "FOREIGN KEY(pro_name) REFERENCES district(dist_name)" +
                " );");

        //create table works_at for store working areas of doctors
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS works_at(" +
                "doc_id VARCHAR," +
                "dis_id VARCHAR," +
                "hospital_name VARCHAR NOT NULL," +
                "FOREIGN KEY(doc_id) REFERENCES doctor(doc_id)," +
                "FOREIGN KEY(dis_id) REFERENCES district(dis_id)," +
                "PRIMARY KEY(doc_id,dis_id)" +
                " );");

        //create a reported table representing the relationship between epidemic disease and doctor
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS reported(" +
                "doc_id VARCHAR," +
                "disease_name VARCHAR," +
                "FOREIGN KEY(pro_name) REFERENCES district(dist_name)" +
                " );");

        //create the works_at table to represent relationship between health officer and city
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS works_for(" +
                "postal_code VARCHAR," +
                "id VARCHAR," +
                "reg_no VARCHAR," +
                "PRIMARY KEY(postal_code,id),"+
                "FOREIGN KEY(postal_code) REFERENCES city(postal_code)," +
                "FOREIGN KEY(id) REFERENCES health_officer(id)" +
                " );");

        //create table for representing the relationship between doctor and patient
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS works_for(" +
                "pat_id VARCHAR," +
                "doc_id VARCHAR," +
                "PRIMARY KEY(pat_id, doc_id),"+
                "FOREIGN KEY(pat_id) REFERENCES patient(pat_id)," +
                "FOREIGN KEY(doc_id) REFERENCES doctor(doc_id)" +
                " );");

        //create table to store data representing the relation between epidemic disease and age group
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS infects_to(" +
                "disease_id VARCHAR," +
                "age_group_id VARCHAR," +
                "PRIMARY KEY(disease_id, age_group_id),"+
                "FOREIGN KEY(disease_id) REFERENCES epidemic_disease(pat_id)," +
                "FOREIGN KEY(age_group_id) REFERENCES age_group(age_group_id)" +
                " );");

        //create table to represent belongs relationship data between patient and age group
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS belongs(" +
                "pat_id VARCHAR," +
                "age_range_id VARCHAR," +
                "PRIMARY KEY(pat_id, age_range_id),"+
                "FOREIGN KEY(pat_id) REFERENCES patient(pat_id)," +
                "FOREIGN KEY(age_range_id) REFERENCES doctor(doc_id)" +
                " );");

        //create table to represent lives in relationship
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS lives_in(" +
                "pat_id VARCHAR," +
                "postal_code VARCHAR," +
                "PRIMARY KEY(pat_id, postal_code),"+
                "FOREIGN KEY(pat_id) REFERENCES patient(pat_id)," +
                "FOREIGN KEY(postal_code) REFERENCES city(postal_code)" +
                " );");


    }
}

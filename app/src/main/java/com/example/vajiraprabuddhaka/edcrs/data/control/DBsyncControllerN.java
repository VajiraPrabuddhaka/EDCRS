package com.example.vajiraprabuddhaka.edcrs.data.control;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vajira Prabuddhaka on 12/22/2016.
 */

public class DBsyncControllerN {
    private SQLiteDatabase db;
    public DBsyncControllerN(SQLiteDatabase db){ //the created database should be given here
        this.db = db;
    }

    private void insertDiseases(HashMap<String, String> queryValues, String disease, String description, String disease_type) {
        ContentValues values = new ContentValues();
        values.put("disease_name", disease);
        values.put("description", description);
        values.put("type", disease_type);
        db.insert("users", null, values);
    }

    private void insertPatients(HashMap<String, String> queryValues, String name, String age, String nationalID, String city, String district) {
        ContentValues values = new ContentValues();
        values.put("full_name", name);
        values.put("age", age);
        values.put("national_id", nationalID);
        values.put("city", city);
        values.put("district", district);
        db.insert("users", null, values);
    }

    private ArrayList<HashMap<String, String>> getAllDiseases() {
        ArrayList<HashMap<String, String>> diseasesList;
        diseasesList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM epidemic_diseases";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("disease_name", cursor.getString(0));
                map.put("description", cursor.getString(1));
                map.put("disease_type", cursor.getString(2));
                diseasesList.add(map);
            } while (cursor.moveToNext());
        }
        return diseasesList;
    }

    private ArrayList<HashMap<String, String>> getAllPatients() {
        ArrayList<HashMap<String, String>> patientsList;
        patientsList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM patients";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("full_name", cursor.getString(0));
                map.put("national_id", cursor.getString(1));
                map.put("age", cursor.getString(2));
                map.put("city", cursor.getString(3));
                map.put("district", cursor.getString(4));
                patientsList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return patientsList;
    }

    private String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync neededn";
        }
        return msg;
    }

    private int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM disease where udpateStatus = '"+"no"+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        count = cursor.getCount();
        return count;
    }

    public void updateSyncStatus(String id, String status){
        String updateQuery = "Update disease set udpateStatus = '"+ status +"' where userId="+"'"+ id +"'";
        Log.d("query",updateQuery);
        db.execSQL(updateQuery);
    }





}

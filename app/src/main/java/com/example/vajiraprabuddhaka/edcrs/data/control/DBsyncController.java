package com.example.vajiraprabuddhaka.edcrs.data.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vajira Prabuddhaka on 12/21/2016.
 */

public class DBsyncController extends SQLiteOpenHelper {
    public DBsyncController(Context applicationcontext) {
        super(applicationcontext, "androidsqlite.db", null, 1);
    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS epidemic_diseases ( disease_name VARCHAR PRIMARY KEY, description VARCHAR, disease_type VARCHAR);" +
                "CREATE TABLE IF NOT EXISTS patients(national_id VARCHAR PRIMARY KEY, full_name VARCHAR, age VARCHAR, city VARCHAR, district VARCHAR)";
        database.execSQL(query);
    }

    //this method calls when alter table executed
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS users";
        database.execSQL(query);
        onCreate(database);
    }
    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public void insertDiseases(HashMap<String, String> queryValues, String disease, String description, String disease_type) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("disease_name", disease);
        values.put("description", description);
        values.put("type", disease_type);
        database.insert("users", null, values);
        database.close();
    }

    public void insertPatients(HashMap<String, String> queryValues, String name, String age, String nationalID, String city, String district) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", name);
        values.put("age", age);
        values.put("national_id", nationalID);
        values.put("city", city);
        values.put("district", district);
        database.insert("users", null, values);
        database.close();
    }

    /**
     * Get list of epedemic deseases from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllDiseases() {
        ArrayList<HashMap<String, String>> diseasesList;
        diseasesList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM epidemic_diseases";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("disease_name", cursor.getString(0));
                map.put("description", cursor.getString(1));
                map.put("disease_type", cursor.getString(2));
                diseasesList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return diseasesList;
    }

    public ArrayList<HashMap<String, String>> getAllPatients() {
        ArrayList<HashMap<String, String>> patientsList;
        patientsList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM patients";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
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
        database.close();
        return patientsList;
    }
        /**
         * Compose JSON out of SQLite records
         * @return
         */


    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync neededn";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM disease where udpateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    /**
     * Update Sync status against each User ID
     * @param id
     * @param status
     */
    public void updateSyncStatus(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update disease set udpateStatus = '"+ status +"' where userId="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

}

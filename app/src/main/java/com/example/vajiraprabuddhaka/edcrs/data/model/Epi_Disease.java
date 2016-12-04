package com.example.vajiraprabuddhaka.edcrs.data.model;

/**
 * Created by root on 12/3/16.
 */

public class Epi_Disease {
    private final String disease_name;
    private final String type;


    public Epi_Disease(String disease_name, String type) {
        this.disease_name = disease_name;
        this.type = type;
    }

    public String getDisease_name() {
        return disease_name;
    }
    public String getType(){ return type; }

}

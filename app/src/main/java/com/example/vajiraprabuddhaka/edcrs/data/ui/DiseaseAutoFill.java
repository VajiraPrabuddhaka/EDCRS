package com.example.vajiraprabuddhaka.edcrs.data.ui;


import java.util.List;

public class DiseaseAutoFill {
    private String[] types={"Leg","Brain"};
    private String[] diseases={"Pissu", "Gon", "Meti"};

    /*public DiseaseAutoFill(){
        types = "";
        diseases = "";
    }*/

    public void populateTypes(){
        /*
            * get from Sql
            * setTypes();
         */
    }

    public void populateDiseases(){
        /*
        * get from sql
        * setDiseases();
         */
    }


    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getDiseases() {
        return diseases;
    }

    public void setDiseases(String[] diseases) {
        this.diseases = diseases;
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

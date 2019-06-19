package com.example.monitoringtanaman;

public class dataFireBase {
    public String phSens;
    public String ecSens;
    public String waterLvl;


    public dataFireBase(String phSens, String ecSens, String waterLvl) {
        this.waterLvl = waterLvl;
        this.phSens = phSens;
        this.ecSens = ecSens;
    }
    public  dataFireBase(){

    }

    public String getPhSens() {
        return phSens;
    }

    public void setPhSens(String phSens) {
        this.phSens = phSens;
    }

    public String getEcSens() {
        return ecSens;
    }

    public void setEcSens(String ecSens) {
        this.ecSens = ecSens;
    }

    public String getWaterLvl() {
        return waterLvl;
    }

    public void setWaterLvl(String waterLvl) {
        this.waterLvl = waterLvl;
    }
}

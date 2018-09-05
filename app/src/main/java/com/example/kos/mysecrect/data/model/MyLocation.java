package com.example.kos.mysecrect.data.model;

import java.io.Serializable;

public class MyLocation implements Serializable {
    private double latng;
    private double longt;

   
    public MyLocation(){
    }
    
    public  MyLocation(double la, double lo){
        this.latng = la;
        this.longt = lo;
    }

    public double getLatng() {
        return latng;
    }

    public double getLongt() {
        return longt;
    }

    public void setLatng(double latng) {
        this.latng = latng;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }
}

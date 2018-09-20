package com.example.kos.mysecrect.data.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserD implements Serializable {
    private String email;
    private String id;
    private String pw;
    private List<DataPWD> listData;
    private double latng;
    private double longt;
    private String bitmap;

    public  UserD(){
        this.email = "";
        this.id = "";
        this.listData = new ArrayList<>();
        this.latng = 0;
        this.longt = 0;
        this.pw = "";
        this.bitmap = "";
    }
    public UserD(String id,String email,List<DataPWD> lsdata,String pw){
        this.email = email;
        this.id = id;
        this.listData = lsdata;
        this.latng = 0;
        this.longt = 0;
        this.pw = pw;
        this.bitmap = "";
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DataPWD> getListData() {
        return listData;
    }

    public String getEmail() {
        return email;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setListData(List<DataPWD> listData) {
        this.listData = listData;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public void setLatng(double latng) {
        this.latng = latng;
    }

    public double getLatng() {
        return latng;
    }

    public double getLongt() {
        return longt;
    }
}

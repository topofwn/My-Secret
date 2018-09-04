package com.example.kos.mysecrect.data.model;

import java.io.Serializable;
import java.util.List;

public class UserD implements Serializable {
    private String email;

    private String id;
    private List<DataPWD> listData;

    public void UserD(){

    }
    public void UserD(String id,String email,List<DataPWD> lsdata){
        this.email = email;
        this.id = id;

        this.listData = lsdata;
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

    public String getId() {
        return id;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setListData(List<DataPWD> listData) {
        this.listData = listData;
    }


}

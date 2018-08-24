package com.example.kos.mysecrect.data.model;

import java.io.Serializable;

public class DataPWD implements Serializable{
    private String fieldName;
    private String encrytedKey;


    public String getEncrytKey() {
        return encrytedKey;
    }

    public String getFieldName() {
        return fieldName;
    }


    public void setEncrytKey(String encrytKey) {
        this.encrytedKey = encrytKey;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


}

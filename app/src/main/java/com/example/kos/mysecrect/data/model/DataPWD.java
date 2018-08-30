package com.example.kos.mysecrect.data.model;

import java.io.Serializable;

public class DataPWD implements Serializable{
    private String fieldName;
    private String encrytedKey;
    private String myKeySpec;

    public DataPWD(){
        this.fieldName = "";
        this.encrytedKey= "";
        this.myKeySpec = "";
    }

    public DataPWD(String name, String key, String keySpec)
    {
        this.fieldName = name;
        this.encrytedKey = key;
        this.myKeySpec = keySpec;
    }
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

    public String getMyKeySpec() {
        return myKeySpec;
    }

    public void setMyKeySpec(String myKeySpec) {
        this.myKeySpec = myKeySpec;
    }
}

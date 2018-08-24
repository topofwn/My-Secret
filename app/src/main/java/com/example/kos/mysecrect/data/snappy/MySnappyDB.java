package com.example.kos.mysecrect.data.snappy;


import com.example.kos.mysecrect.data.model.DataPWD;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Created by giaotuan on 12/10/16.
 */

public interface MySnappyDB {
    void setDeviceId(String deviceid);
    String getDeviceId();
    void setListData(List<DataPWD> listdata);
    List<DataPWD> getListData();

}
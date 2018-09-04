package com.example.kos.mysecrect.data;


import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.data.snappy.MySnappyDB;

import java.util.List;


/**
 * Created by tuan.giao on 11/8/2017.
 */

public interface DataManager extends  MySnappyDB {
    void setDeviceId(String deviceid);
    String getDeviceId();
    void setListData(List<DataPWD> listdata);
    List<DataPWD> getListData();
    UserD getUser();
    void setUser(UserD user);
}

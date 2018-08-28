package com.example.kos.mysecrect.data;

import android.content.Context;


import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.snappy.MySnappyImpl;

import java.util.List;

/**
 * Created by tuan.giao on 11/9/2017.
 */

public class DataManagerImpl implements DataManager {

    private static DataManagerImpl mInstance;

    private Context mContext;

    private MySnappyImpl mySnappy;




    public DataManagerImpl(Context context) {

        this.mySnappy = MySnappyImpl.newInstance(context);

    }

    public static DataManagerImpl getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataManagerImpl(context);
        }
        return mInstance;
    }

    @Override
    public void setDeviceId(String deviceid) {
        mySnappy.setDeviceId(deviceid);
    }

    @Override
    public String getDeviceId() {
        return mySnappy.getDeviceId();
    }

    @Override
    public void setListData(List<DataPWD> listdata) {
        mySnappy.setListData(listdata);
    }

    @Override
    public List<DataPWD> getListData() {
        return mySnappy.getListData();
    }
}

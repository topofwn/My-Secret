package com.example.kos.mysecrect.data.snappy;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.MyLocation;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.utils.OGILVYLog;
import com.snappydb.DB;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.kos.mysecrect.data.snappy.SnapKey.MY_SNAPPY_KEY_DEVICE_ID;
import static com.example.kos.mysecrect.data.snappy.SnapKey.MY_SNAPPY_KEY_LIST_DATA;
import static com.example.kos.mysecrect.data.snappy.SnapKey.MY_SNAPPY_KEY_LOCATION;
import static com.example.kos.mysecrect.data.snappy.SnapKey.MY_SNAPPY_KEY_USER;


/**
 * Created by giaotuan on 12/10/16.
 */

public class MySnappyImpl implements MySnappyDB {

    private DB snappydb;
    private static MySnappyImpl mInstance;

    public static synchronized MySnappyImpl newInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new MySnappyImpl(context);
        }
        return mInstance;
    }

    private MySnappyImpl(Context application) {
        if (snappydb == null) {
            try {
                snappydb = new SnappyDB.Builder(application).name("MySecret").build();
            } catch (SnappydbException e) {
                OGILVYLog.l(e);
            }
        }
    }
    public void putString(String key, String value) {

        try {
            snappydb.put(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }


    }
    public void putInt(String key, int value) {

        try {
            snappydb.putInt(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }


    }


    private int getInt(String key){
        try {
            return snappydb.getInt(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private String getString(String key){
        try {
            return snappydb.get(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return null;
    }
    private <T extends Serializable> T getObject(String key, Class<T> className) {
        try {
            T obj = snappydb.get(key, className);
            return obj;
        } catch (SnappydbException e) {
            OGILVYLog.logTuan("getObject", e.getMessage() + key, MySnappyImpl.class);
        }
        return null;
    }


    private void saveObject(Object obj, String key) {
        try {
            snappydb.put(key, obj);
        } catch (SnappydbException e) {
            OGILVYLog.logTuan("saveObject", e.getMessage() + key, MySnappyImpl.class);
        }
    }


    private void removeObjectByKey(String key) {
        try {
            snappydb.del(key);
        } catch (SnappydbException e) {
            OGILVYLog.logTuan("removeObjectByKey", e.getMessage() + key, MySnappyImpl.class);
        }
    }


    @Override
    public void setDeviceId(String deviceid) {
        putString(MY_SNAPPY_KEY_DEVICE_ID,deviceid);
    }

    @Override
    public String getDeviceId() {
        return getString(MY_SNAPPY_KEY_DEVICE_ID);
    }

    @Override
    public void setListData(List<DataPWD> listdata) {
        synchronized (snappydb) {
            try {
                snappydb.put(MY_SNAPPY_KEY_LIST_DATA, listdata.toArray());

            } catch (SnappydbException e) {
                OGILVYLog.logTuan("setData", e.getMessage(), MySnappyImpl.class);

            }
        }
    }

    @Override
    public List<DataPWD> getListData() {
        synchronized (snappydb) {
            try {
                if (snappydb.exists(MY_SNAPPY_KEY_LIST_DATA)) {
                    DataPWD[] array = snappydb.getArray(MY_SNAPPY_KEY_LIST_DATA, DataPWD.class);
                    if (array != null) {
                        return new ArrayList<>(Arrays.asList(array));
                    }
                }
            } catch (SnappydbException e) {
                OGILVYLog.logTuan("getData", e.getMessage(), MySnappyImpl.class);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void setUser(UserD user) {
        saveObject(user,MY_SNAPPY_KEY_USER);
    }

    @Override
    public UserD getUser() {
        return getObject(MY_SNAPPY_KEY_USER,UserD.class);
    }

    @Override
    public MyLocation getMyLocation() {
        return getObject(MY_SNAPPY_KEY_LOCATION,MyLocation.class);
    }

    @Override
    public void setMyLocation(MyLocation location) {
        saveObject(location,MY_SNAPPY_KEY_LOCATION);
    }
}



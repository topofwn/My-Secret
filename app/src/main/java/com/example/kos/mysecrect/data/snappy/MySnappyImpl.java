package com.example.kos.mysecrect.data.snappy;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.kos.mysecrect.utils.OGILVYLog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.snappydb.DB;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

import java.io.Serializable;

import static com.example.kos.mysecrect.data.snappy.SnapKey.MY_SNAPPY_KEY_FIREBASE_INSTANCE;


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


}



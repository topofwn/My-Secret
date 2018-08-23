package com.example.kos.mysecrect.utils;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.DataManagerImpl;


/**
 * Created by tuan.giao on 11/8/2017.
 */

public class Injections {



    public static DataManager provideAppDataManager(@NonNull Context context) {
        return DataManagerImpl.getInstance(context);
    }

    public static SchedulerProvider provideSchedulerProvider() {
        return AppSchedulerProvider.getInstance();
    }


}

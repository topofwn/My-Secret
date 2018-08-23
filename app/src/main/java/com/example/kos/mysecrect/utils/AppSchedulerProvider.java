package com.example.kos.mysecrect.utils;

import android.support.annotation.Nullable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tuan.giao on 5/8/2017.
 */

public class AppSchedulerProvider implements SchedulerProvider {

    @Nullable
    private static SchedulerProvider INSTANCE;

    // Prevent direct instantiation.
    private AppSchedulerProvider() {
    }

    public static synchronized SchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppSchedulerProvider();
        }
        return INSTANCE;
    }


    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

}

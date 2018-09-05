package com.example.kos.mysecrect.ui.splash;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.MyLocation;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

import java.util.List;

public class SplashPresenter <V extends SplashContract.View> extends BasePresenter<V> implements SplashContract.Presenter<V> {

    public SplashPresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider,dataManager);
    }

    public SplashPresenter() {
        super();
    }



    @Override
    public void setMyDeviceId(String id) {
        dataManager.setDeviceId(id);
        getMvpView().updateProgressBar(50);
    }

    @Override
    public void setListData(List<DataPWD> data) {
        dataManager.setListData(data);
    }

    @Override
    public List<DataPWD> getListData() {
        return dataManager.getListData();
    }

    @Override
    public void setLocation(double la, double lo) {
        MyLocation location = new MyLocation(la,lo);
        dataManager.setMyLocation(location);
        getMvpView().updateProgressBar(60);
    }


}


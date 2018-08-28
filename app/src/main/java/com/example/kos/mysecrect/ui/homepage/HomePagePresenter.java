package com.example.kos.mysecrect.ui.homepage;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

import java.util.List;

public class HomePagePresenter <V extends HomePageContract.View> extends BasePresenter<V> implements HomePageContract.Presenter<V>{
    public HomePagePresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public HomePagePresenter() {
        super();
    }


    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
    }


    @Override
    public void setMyDeviceId(String id) {
        dataManager.setDeviceId(id);
    }

    @Override
    public void setListData(List<DataPWD> lData) {
        dataManager.setListData(lData);
    }
}

package com.example.kos.mysecrect.ui.homepage;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.MyLocation;
import com.example.kos.mysecrect.data.model.UserD;
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
    public UserD getUser() {
        return dataManager.getUser();
    }

    @Override
    public void setListData(List<DataPWD> data) {
        dataManager.setListData(data);
    }

    @Override
    public MyLocation getMyLocation() {
        return dataManager.getMyLocation();
    }

    @Override
    public void setUser(UserD user) {
        dataManager.setUser(user);
    }

}

package com.example.kos.mysecrect.ui.manualgenerate;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

import java.util.List;

public class ManualGeneratePresenter<V extends ManualGenerateContract.View> extends BasePresenter<V> implements ManualGenerateContract.Presenter<V> {
    public ManualGeneratePresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public ManualGeneratePresenter() {
        super();
    }


    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
    }


    @Override
    public List<DataPWD> getList() {
        return dataManager.getListData();
    }

    @Override
    public void setList(List<DataPWD> data) {
        dataManager.setListData(data);
    }

    @Override
    public String getMyDeviceId() {
        return dataManager.getDeviceId();
    }

    @Override
    public UserD getUser() {
        return dataManager.getUser();
    }

    @Override
    public void setUser(UserD user) {
        dataManager.setUser(user);
    }
}

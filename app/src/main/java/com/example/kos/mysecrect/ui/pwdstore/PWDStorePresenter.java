package com.example.kos.mysecrect.ui.pwdstore;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

import java.util.List;

public class PWDStorePresenter <V extends PWDStoreContract.View> extends BasePresenter<V> implements PWDStoreContract.Presenter<V> {



    public PWDStorePresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public PWDStorePresenter() {
        super();
    }


    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
    }


    @Override
    public List<DataPWD> getListData() {
        return dataManager.getListData();
    }

    @Override
    public void setListData(List<DataPWD> data) {
        dataManager.setListData(data);
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

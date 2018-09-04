package com.example.kos.mysecrect.ui.registration;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.ui.login.LoginContract;
import com.example.kos.mysecrect.utils.SchedulerProvider;

import java.util.List;

public class RegistrationPresenter<V extends RegistrationContract.View> extends BasePresenter<V> implements RegistrationContract.Presenter<V>{




    public RegistrationPresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public RegistrationPresenter() {
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
    public void setUser(UserD user) {
        dataManager.setUser(user);
    }
}

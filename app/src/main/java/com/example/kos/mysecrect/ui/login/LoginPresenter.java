package com.example.kos.mysecrect.ui.login;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter<V> {
    private FirebaseAuth mAuth ;

    public LoginPresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public LoginPresenter() {
        super();
    }


    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            if(mAuth.getCurrentUser().isEmailVerified()) {
                getMvpView().gotoHomePage();
            }
        }
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
    public void setUser(UserD user) {
        dataManager.setUser(user);
    }
}

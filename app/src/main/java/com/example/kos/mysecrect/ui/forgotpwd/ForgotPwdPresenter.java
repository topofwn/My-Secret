package com.example.kos.mysecrect.ui.forgotpwd;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

public class ForgotPwdPresenter <V extends ForgotPwdContract.View> extends BasePresenter<V> implements ForgotPwdContract.Presenter<V> {
    public ForgotPwdPresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public ForgotPwdPresenter() {
        super();
    }


    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
    }

}

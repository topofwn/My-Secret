package com.example.kos.mysecrect.ui.generatekey;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;


public class GenerateKeyPresenter <V extends GenerateKeyContract.View> extends BasePresenter<V> implements GenerateKeyContract.Presenter<V> {

    public GenerateKeyPresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public GenerateKeyPresenter() {
        super();
    }


    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
    }





}

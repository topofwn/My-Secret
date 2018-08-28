package com.example.kos.mysecrect.ui.manualgenerate;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

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


}

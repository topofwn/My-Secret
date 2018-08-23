package com.example.kos.mysecrect.ui.homepage;

import android.support.annotation.NonNull;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.ui.base.MVPView;
import com.example.kos.mysecrect.utils.SchedulerProvider;

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
    public void saveCommand(@NonNull String command) {

    }
}

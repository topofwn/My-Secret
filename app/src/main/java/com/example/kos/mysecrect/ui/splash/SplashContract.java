package com.example.kos.mysecrect.ui.splash;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.util.List;

public interface SplashContract {
    interface Presenter<V extends View> extends MVPPresenter<V> {
        void setMyDeviceId(String id);
        void setListData(List<DataPWD> data);
        String getMyDeviceId();

    }
    interface View extends MVPView {
        void updateProgressBar(int count);
        void gotoHomePage();
    }

}

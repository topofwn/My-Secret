package com.example.kos.mysecrect.ui.homepage;

import android.support.annotation.NonNull;

import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

public interface HomePageContract {
    interface Presenter<V extends View> extends MVPPresenter<V> {
    void setMyDeviceId(String id);

    }
    interface View extends MVPView {

    }
}

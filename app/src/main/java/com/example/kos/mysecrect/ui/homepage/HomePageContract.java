package com.example.kos.mysecrect.ui.homepage;

import android.support.annotation.NonNull;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.util.List;

public interface HomePageContract {
    interface Presenter<V extends View> extends MVPPresenter<V> {
    void setMyDeviceId(String id);
    void setListData(List<DataPWD> lData);

    }
    interface View extends MVPView {

    }
}

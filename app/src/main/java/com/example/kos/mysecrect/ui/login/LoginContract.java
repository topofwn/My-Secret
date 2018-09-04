package com.example.kos.mysecrect.ui.login;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.util.List;

public interface LoginContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void setListData(List<DataPWD> data);
        List<DataPWD> getListData();
        void setUser(UserD user);

    }
    interface View extends MVPView {
        void gotoRegistration();
        void gotoHomePage();
    }
}

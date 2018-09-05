package com.example.kos.mysecrect.ui.registration;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.util.List;


public interface RegistrationContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {
        List<DataPWD> getListData();
        void setUser(UserD user);
        void setListData(List<DataPWD> data);

    }
    interface View extends MVPView {
        void gotoHomepage();
    }
}

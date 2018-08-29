package com.example.kos.mysecrect.ui.pwdstore;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.util.List;


public interface PWDStoreContract {
    interface Presenter<V extends View> extends MVPPresenter<V> {
        List<DataPWD> getListData();




    }
    interface View extends MVPView {

    }
}

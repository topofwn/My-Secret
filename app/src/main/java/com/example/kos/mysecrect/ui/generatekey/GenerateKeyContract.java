package com.example.kos.mysecrect.ui.generatekey;


import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;


public interface GenerateKeyContract  {
    interface Presenter<V extends GenerateKeyContract.View> extends MVPPresenter<V>

    {


    }
    interface View extends MVPView {

    }
}

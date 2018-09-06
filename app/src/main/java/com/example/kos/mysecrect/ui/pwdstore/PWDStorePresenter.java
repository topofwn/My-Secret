package com.example.kos.mysecrect.ui.pwdstore;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

import javax.crypto.Cipher;

public class PWDStorePresenter <V extends PWDStoreContract.View> extends BasePresenter<V> implements PWDStoreContract.Presenter<V> {



    public PWDStorePresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        super(schedulerProvider, dataManager);
    }

    public PWDStorePresenter() {
        super();
    }


    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
    }


    @Override
    public List<DataPWD> getListData() {
        return dataManager.getListData();
    }


}

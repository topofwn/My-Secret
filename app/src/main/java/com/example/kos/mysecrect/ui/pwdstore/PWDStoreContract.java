package com.example.kos.mysecrect.ui.pwdstore;

import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public interface PWDStoreContract {
    interface Presenter<V extends View> extends MVPPresenter<V> {


        String getDecrypt(String output) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;


    }
    interface View extends MVPView {

    }
}

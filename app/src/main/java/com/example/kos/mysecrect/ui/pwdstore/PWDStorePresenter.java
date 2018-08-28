package com.example.kos.mysecrect.ui.pwdstore;

import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.ui.base.BasePresenter;
import com.example.kos.mysecrect.utils.SchedulerProvider;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PWDStorePresenter <V extends PWDStoreContract.View> extends BasePresenter<V> implements PWDStoreContract.Presenter<V> {
    private KeyPairGenerator kpg;
    private KeyPair kp;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private byte[] encryptedBytes, decryptedBytes;
    private Cipher cipher, cipher1;
    private String encrypted, decrypted;



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
    public String getDecrypt(String output) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher1=Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        decryptedBytes = cipher1.doFinal(stringToBytes(output));
        decrypted = new String(decryptedBytes);
        return decrypted;
    }

    @Override
    public String getDeveiceId() {
        return dataManager.getDeviceId();
    }


    private byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }
}

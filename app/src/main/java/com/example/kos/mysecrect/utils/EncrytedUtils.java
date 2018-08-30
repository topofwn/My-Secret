package com.example.kos.mysecrect.utils;

import android.util.Base64;

import com.example.kos.mysecrect.data.model.DataPWD;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncrytedUtils {

  private static String AES = "AES";

    public static String Decrypt(DataPWD mData) throws Exception {
        SecretKeySpec key = generateKeySpec(mData.getMyKeySpec());
        Cipher cipher1=Cipher.getInstance(AES);
        cipher1.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeval = Base64.decode(mData.getEncrytKey(),Base64.DEFAULT);
        byte[] decryptedBytes = cipher1.doFinal(decodeval);
        String value = new String(decryptedBytes);
        return value;
    }


    public static SecretKeySpec generateKeySpec(String key) throws  Exception{
    final MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] bytes = key.getBytes();
    digest.update(bytes,0,bytes.length);
    byte[] keySpec = digest.digest();
    return new SecretKeySpec(keySpec,"AES");
    }

    public static DataPWD Encrypt (DataPWD mData) throws Exception {
        SecretKeySpec key = generateKeySpec(mData.getMyKeySpec());
        DataPWD newData = new DataPWD();
        newData.setMyKeySpec(mData.getMyKeySpec());
        newData.setFieldName(mData.getFieldName());
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(mData.getEncrytKey().getBytes());
        String encrypted = android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT);
        newData.setEncrytKey(encrypted);
        return newData;

    }

}

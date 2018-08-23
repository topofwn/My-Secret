package com.example.kos.mysecrect.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SecurityUtils {

    public static String convertSha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexStr = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexStr.append('0');
                hexStr.append(hex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            OGILVYLog.logEx(ex, SecurityUtils.class);
            return "";
        }
    }
}

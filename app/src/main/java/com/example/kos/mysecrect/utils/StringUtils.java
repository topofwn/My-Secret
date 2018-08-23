package com.example.kos.mysecrect.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static final String EMPTY_STRING = "";

    /**
     * get a key of string from resources
     *
     * @param resID
     * @return key
     */
    public static String getString(Context context, int resID) {
        return context.getResources().getString(resID);
    }


    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) return false;
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static <E extends Enum<E>> E lookup(Class<E> e, String id) {
        try {
            return Enum.valueOf(e, id);
        } catch (IllegalArgumentException ex) {
            // log error or something here
            throw new RuntimeException(
                    "Invalid value for enum " + e.getSimpleName() + ": " + id);
        }
    }

    public static boolean isPwValid(String pw) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return pw.matches(pattern);
    }

    public static InputFilter[] getMaximumCharInputFilter(int max) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(max);
        return fArray;
    }
}

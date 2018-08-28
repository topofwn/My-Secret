package com.example.kos.mysecrect.utils;

import android.os.Environment;

/**
 * Created by tuan.giao on 11/8/2017.
 */

public class AppConstants {
    private static String externalStoragePatch = Environment
            .getExternalStorageDirectory().getPath();


    public static final String API_WEATHER = "189c14032c6fafef2aadda401ebe7223";
    public static final String WEATHER_URL ="http://api.openweathermap.org/data/2.5/weather?lat={latng}&lon={longt}&appid="+API_WEATHER;
    public static final String ICON_URL = "http://openweathermap.org/img/w/";

    public static final String PATH_PARAM_LATNG = "latng";
    public static final String PATH_PARAM_LONGT = "longt";
    public static final String PICK_COLOR = "pick_color";

    public static final String FIELD_NAME = "fielName";
    public static final String ENCRYPTED_KEY = "encryptedKey";

    public static final long SCAN_PERIOD = 10000; //10 seconds

    private static final String EXTERNAL_FOLDER_SAVE_LANGUAGE_PACKAGE_NAME = externalStoragePatch + "/download";
    public static final String EXTERNAL_FOLDER_SAVE_LANGUAGE_PACKAGE = EXTERNAL_FOLDER_SAVE_LANGUAGE_PACKAGE_NAME + "/";

}

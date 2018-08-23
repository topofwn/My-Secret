package com.example.kos.mysecrect.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by tuan.giao on 2/1/2018.
 */

public class DeviceUtils {

    public static Point getScreenSize(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        return screenSize;
    }
}

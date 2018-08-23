package com.example.kos.mysecrect.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;

public class DrawableUtils {

    @SuppressWarnings("deprecation")
    public static Drawable resolveDrawable(@DrawableRes int resId, Context context) {
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();

        if (Build.VERSION.SDK_INT >= 22) {
            return resources.getDrawable(resId, theme);
        }
        else {
            return resources.getDrawable(resId);
        }
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1, (float) source.getWidth()/2, (float) source.getHeight()/2);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}

package com.example.kos.mysecrect.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Tuan.Giao on 12/28/2016.
 */

public class IOUtils {
    /**
     * encode a bitmap to base64 string
     * @param image
     * @return base64 string
     */
    public static String encodeBitmapToBase64(Bitmap image) {
        if (image == null) return  null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded.replace("\n", "");
    }

    /**
     * decode base64 string to bitmap
     * @param input
     * @return bitmap
     */
    public static Bitmap decodeBase64ToBitmap(String input) {
        if (input == null) return null;
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0,decodedByte.length);
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}

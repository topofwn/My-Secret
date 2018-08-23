package com.example.kos.mysecrect.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.example.kos.mysecrect.BuildConfig;


/**
 * Utility class for getting system information.
 */
public final class SystemUtils {
    /**
     * Hide default constructor.
     */
    private SystemUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * get version code of application
     *
     * @return version code
     */
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * get version name of application
     *
     * @return version
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * Get information if Android version is Kitkat (4.4).
     *
     * @return true if Kitkat.
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }

    /**
     * Get information if Android version is Lollipop (5.0) or higher.
     *
     * @return true if Lollipop or higher.
     */
    public static boolean isAndroid5() {
        return isAtLeastVersion(Build.VERSION_CODES.LOLLIPOP);
    }

    /**
     * Check if Android version is at least the given version.
     *
     * @param version The version
     * @return true if Android version is at least the given version
     */
    private static boolean isAtLeastVersion(final int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    /**
     * check connection is available
     *
     * @return true if a connection is available
     * @author tuan.giao
     */
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null){
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            return activeNetwork != null;
        }
        return false;
    }

}

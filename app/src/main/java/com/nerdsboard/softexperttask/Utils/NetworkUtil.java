package com.nerdsboard.softexperttask.Utils;


import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return  cm != null &&
                cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}

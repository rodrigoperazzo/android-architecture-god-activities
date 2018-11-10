package com.rperazzo.weatherapp.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Thiago on 10/11/2018.
 */

public class ConnectivityUtil implements IConnectivityUtil {

    private Context context;

    public ConnectivityUtil(Context context){
        this.context = context;
    }

    @Override
    public boolean isDeviceConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}

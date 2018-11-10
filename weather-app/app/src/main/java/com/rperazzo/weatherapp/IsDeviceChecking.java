package com.rperazzo.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class IsDeviceChecking implements IisConnect {

    Context content;

    public IsDeviceChecking(Context content){
        this.content = content;
    }

    @Override
    public boolean isDeviceConnected() {

        ConnectivityManager cm = (ConnectivityManager)
                content.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}

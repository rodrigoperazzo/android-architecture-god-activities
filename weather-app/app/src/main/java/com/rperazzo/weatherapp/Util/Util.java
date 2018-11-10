package com.rperazzo.weatherapp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

    private static final String TEMPERATURE_UNIT_KEY = "TEMPERATURE_UNIT_KEY";
    private static final String PREFERENCE_NAME = "com.rperazzo.weatherapp.shared";

    public static boolean isDeviceConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager)
                c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public static void setTemperatureUnit(String value, Context c) {
        SharedPreferences mSharedPref = c.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(TEMPERATURE_UNIT_KEY, value);
        editor.apply();
    }

    public static String getTemperatureUnit(Context c) {
        SharedPreferences mSharedPref = c.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mSharedPref.getString(TEMPERATURE_UNIT_KEY, "metric");
    }
}

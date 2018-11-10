package com.rperazzo.weatherapp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Thiago on 10/11/2018.
 */

public class TemperatureSharedPref {


    private static final String PREFERENCE_NAME = "com.rperazzo.weatherapp.shared";

    public TemperatureSharedPref(Context context) {
        mSharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private static final String TEMPERATURE_UNIT_KEY = "TEMPERATURE_UNIT_KEY";
    private static final String TEMPERATURE_LANG_KEY = "TEMPERATURE_LANG_KEY";

    private SharedPreferences mSharedPref;

    public void setTemperatureUnit(String value) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(TEMPERATURE_UNIT_KEY, value);
        editor.apply();
    }

    public String getTemperatureUnit() {
        return mSharedPref.getString(TEMPERATURE_UNIT_KEY, "metric");
    }

    public void setTemperatureLang(String value) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(TEMPERATURE_LANG_KEY, value);
        editor.apply();
    }

    public String getTemperatureLang() {
        return mSharedPref.getString(TEMPERATURE_LANG_KEY, "lang");
    }
}

package com.rperazzo.weatherapp.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import com.rperazzo.weatherapp.Util.Util;
import com.rperazzo.weatherapp.View.IView;
import com.rperazzo.weatherapp.WeatherManager;

public class MainActivityPresenter implements IPresenter {

    private static final String TEMPERATURE_UNIT_KEY = "TEMPERATURE_UNIT_KEY";
    private static final String PREFERENCE_NAME = "com.rperazzo.weatherapp.shared";

    private IView _view;
    public MainActivityPresenter(IView view){
        this._view = view;
    }


    public   void setTemperatureUnit(String value) {
        SharedPreferences mSharedPref = _view.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(TEMPERATURE_UNIT_KEY, value);
        editor.apply();
    }

    public   String getTemperatureUnit() {
        SharedPreferences mSharedPref = _view.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mSharedPref.getString(TEMPERATURE_UNIT_KEY, "metric");
    }

    public  boolean isDeviceConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                _view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }


    public void updateUnitIfNecessary(String newUnits, String search) {
        String currentUnits = getTemperatureUnit();
        if (!currentUnits.equals(newUnits)) {
            setTemperatureUnit(newUnits);
            searchByName(search);
        }
    }

    public void searchByName(String search) {
        if (!isDeviceConnected()) {
            this._view.showMessage("No internet");
            return;
        }


        if (TextUtils.isEmpty(search)) {
            return;
        }

        _view.onStartLoading();
        String units = getTemperatureUnit();

        // falta corrigir
        new WeatherManager().getResults(search, units, _view);
    }
}

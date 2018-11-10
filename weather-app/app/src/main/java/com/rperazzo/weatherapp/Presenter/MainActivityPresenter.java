package com.rperazzo.weatherapp.Presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.rperazzo.weatherapp.Util.Util;
import com.rperazzo.weatherapp.View.IView;
import com.rperazzo.weatherapp.WeatherManager;

public class MainActivityPresenter implements IPresenter {

    private IView _view;
    public MainActivityPresenter(IView view){
        this._view = view;
    }

    public void updateUnitIfNecessary(String newUnits, String search) {
        String currentUnits = Util.getTemperatureUnit(_view.getContext());
        if (!currentUnits.equals(newUnits)) {
            Util.setTemperatureUnit(newUnits, _view.getContext());
            searchByName(search);
        }
    }

    public void searchByName(String search) {
        if (!Util.isDeviceConnected(_view.getContext())) {
            this._view.showMessage("No internet");
            return;
        }


        if (TextUtils.isEmpty(search)) {
            return;
        }

        _view.onStartLoading();
        String units = Util.getTemperatureUnit( _view.getContext());

        // falta corrigir
        new WeatherManager().getResults(search, units, _view);
    }
}

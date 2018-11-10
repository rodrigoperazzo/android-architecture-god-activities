package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.Presenter.IWeatherPresenter;
import com.rperazzo.weatherapp.Service.WeatherManager;
import com.rperazzo.weatherapp.Util.IConnectivityUtil;

/**
 * Created by Thiago on 10/11/2018.
 */

public class Presenter implements IWeatherPresenter {

    @Override
    public boolean searchByName(String name, String units, String lang, IConnectivityUtil connect) {
        return false;
    }

    @Override
    public void onResult(WeatherManager.FindResult result) {

    }

    @Override
    public void onResultWithError(String message) {

    }

}

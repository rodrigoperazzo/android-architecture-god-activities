package com.rperazzo.weatherapp.Presenter;

import com.rperazzo.weatherapp.Service.WeatherManager;
import com.rperazzo.weatherapp.Util.IConnectivityUtil;
import com.rperazzo.weatherapp.View.Interface.ISearch;

/**
 * Created by Thiago on 10/11/2018.
 */

public interface IWeatherPresenter {

    boolean searchByName(String name, String units, String lang, IConnectivityUtil connect);
    void onResult(WeatherManager.FindResult result);
    void onResultWithError(String message);

}

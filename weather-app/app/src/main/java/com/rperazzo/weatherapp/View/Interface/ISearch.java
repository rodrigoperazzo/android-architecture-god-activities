package com.rperazzo.weatherapp.View.Interface;

import com.rperazzo.weatherapp.Service.WeatherManager;

/**
 * Created by Thiago on 10/11/2018.
 */

public interface ISearch {

    void onStartLoading();
    void onFinishLoading(WeatherManager.FindResult result);
    void onFinishLoadingWithError();
}

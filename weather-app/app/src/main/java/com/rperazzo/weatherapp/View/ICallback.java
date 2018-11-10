package com.rperazzo.weatherapp.View;

import com.rperazzo.weatherapp.WeatherManager;

public interface ICallback {
    void onFinishLoading(WeatherManager.FindResult result);
    void onFinishLoadingWithError();
}

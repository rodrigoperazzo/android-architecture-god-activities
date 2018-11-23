package com.rperazzo.weatherapp.View;

import android.content.Context;

import com.rperazzo.weatherapp.WeatherManager;

public interface IView {
    void onFinishLoading(WeatherManager.FindResult result);
    void onFinishLoadingWithError();
    Context getContext();
    void onStartLoading();
    void showMessage(String message);
}

package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.Service.WeatherManager;
import com.rperazzo.weatherapp.View.Interface.ISearch;

public class Search implements ISearch {

    public String resultSearch;

    @Override
    public void onStartLoading() {
        resultSearch = "onStartLoading";
    }

    @Override
    public void onFinishLoading(WeatherManager.FindResult result) {
        resultSearch = "onFinishLoading";
    }

    @Override
    public void onFinishLoadingWithError(String message) {
        resultSearch = "onFinishLoadingWithError";
    }
}

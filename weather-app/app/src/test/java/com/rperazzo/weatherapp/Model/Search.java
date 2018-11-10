package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.Service.WeatherManager;
import com.rperazzo.weatherapp.View.Interface.ISearch;

public class Search implements ISearch {
    @Override
    public void onStartLoading() {

    }

    @Override
    public void onFinishLoading(WeatherManager.FindResult result) {

    }

    @Override
    public void onFinishLoadingWithError() {

    }
}

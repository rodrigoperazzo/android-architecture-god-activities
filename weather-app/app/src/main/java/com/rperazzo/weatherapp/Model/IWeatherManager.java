package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.View.ICallback;

public interface IWeatherManager {
    void getResults(String search, String units, final ICallback callback);
}

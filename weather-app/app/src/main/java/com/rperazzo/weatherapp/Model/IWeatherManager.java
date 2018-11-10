package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.View.IView;

public interface IWeatherManager {
    void getResults(String search, String units, final IView callback);
}

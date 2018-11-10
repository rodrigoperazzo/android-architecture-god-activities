package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.Model.Service.IWeatherService;

public interface IWeatherManager {
    IWeatherService getService();
}

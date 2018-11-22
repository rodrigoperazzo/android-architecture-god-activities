package com.rperazzo.weatherapp.controller;

import android.text.TextUtils;

import com.rperazzo.weatherapp.MainActivity;
import com.rperazzo.weatherapp.WeatherManager;
import com.rperazzo.weatherapp.listener.IView;
import com.rperazzo.weatherapp.listener.OnSearchCity;
import com.rperazzo.weatherapp.provider.IWeatherProvider;
import com.rperazzo.weatherapp.service.CityWeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherController {

    private IWeatherProvider wService;
    private WeatherManager manager;
    private IView view;
    private CityWeatherService service;

    public WeatherController(IView iView, CityWeatherService service) {
        this.view = iView;
        this.service = service;
    }

    public void searchByName(String city, String temperatureUnit) {

        if (city.isEmpty()) {
            return;
        }

        //city.length() != 0;

        service.find(city, new OnSearchCity() {
            @Override
            public void onSearchCity() {
                view.update();
            }
        }, temperatureUnit);
    }
}

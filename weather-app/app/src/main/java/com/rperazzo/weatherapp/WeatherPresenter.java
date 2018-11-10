package com.rperazzo.weatherapp;

import android.text.TextUtils;

import com.rperazzo.weatherapp.listener.IView;
import com.rperazzo.weatherapp.listener.OnSearchCity;
import com.rperazzo.weatherapp.model.City;
import com.rperazzo.weatherapp.service.CityWeatherService;

import java.util.List;

public class WeatherPresenter {

    IView view;
    private CityWeatherService service;
    private  IsDeviceChecking check;

    public WeatherPresenter(IView view, CityWeatherService service, IsDeviceChecking check)
    {
        this.view = view;
        this.service = service;
        this.check = check;
    }

    public void searchByName(final String city, String unity) {
        if (city != null && city.isEmpty()) {
            return;
        }
        if(!check.isDeviceConnected()){
            view.mostrarSemInternet();
            return;
        }
        view.mostrarLoading();
        service.find(city, new OnSearchCity() {
            @Override
            public void onSearchCity() {
                List<City> cities = service.getCities();

                if (cities != null && !cities.isEmpty() ) {

                    view.mostrarListaCidade(cities);

                } else {

                    view.mostrarListaVazia();
                }
                view.removerLoading();
            }
        }, unity);

    }
}

package com.rperazzo.weatherapp.model.weather.remote;

import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.presentation.WeatherContract;

import java.util.List;

import io.reactivex.Single;

public interface WeatherRemote {
    Single<List<City>> find(String text, String units);
}

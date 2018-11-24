package com.rperazzo.weatherapp.model.weather;

import com.rperazzo.weatherapp.presentation.WeatherContract;

import java.util.List;

import io.reactivex.Single;

public interface WeatherRepository {
    Single<List<City>> search(String text, String units);
}

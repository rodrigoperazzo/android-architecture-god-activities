package com.rperazzo.weatherapp.model.weather;

import com.rperazzo.weatherapp.model.weather.remote.FindResult;

import io.reactivex.Single;

public interface WeatherRepository {
    Single<FindResult> search(String text, String units);
}

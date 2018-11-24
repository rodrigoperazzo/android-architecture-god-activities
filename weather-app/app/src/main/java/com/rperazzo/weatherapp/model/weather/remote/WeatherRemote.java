package com.rperazzo.weatherapp.model.weather.remote;

import io.reactivex.Single;

public interface WeatherRemote {
    Single<FindResult> find(String text, String units);
}

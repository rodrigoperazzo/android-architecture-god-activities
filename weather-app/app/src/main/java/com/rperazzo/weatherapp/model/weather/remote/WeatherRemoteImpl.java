package com.rperazzo.weatherapp.model.weather.remote;

import io.reactivex.Single;

public class WeatherRemoteImpl implements WeatherRemote {

    WeatherService mWeatherService;

    public  WeatherRemoteImpl() {
        mWeatherService = WeatherManager.getService();
    }

    public Single<FindResult> find(String text, String units) {

        final Single<FindResult> findCall = mWeatherService.find(text, units, WeatherManager.API_KEY);

        return findCall;

    }

}

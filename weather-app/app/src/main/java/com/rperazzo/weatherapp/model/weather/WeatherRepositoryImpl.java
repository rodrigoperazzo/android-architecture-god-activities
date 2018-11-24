package com.rperazzo.weatherapp.model.weather;

import com.rperazzo.weatherapp.model.weather.remote.FindResult;
import com.rperazzo.weatherapp.model.weather.remote.WeatherRemote;
import com.rperazzo.weatherapp.presentation.WeatherContract;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    WeatherRemote mRemote;

    public WeatherRepositoryImpl(WeatherRemote remote) {
        mRemote = remote;
    }

    @Override
    public Single<FindResult> search(String text, String units) {

        if (text != null && !text.isEmpty() && units != null && !units.isEmpty()) {
            return mRemote.find(text, units);
        }

        return null;
    }
}

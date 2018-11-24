package com.rperazzo.weatherapp.model.weather;

import com.rperazzo.weatherapp.model.weather.remote.FindResult;
import com.rperazzo.weatherapp.model.weather.remote.WeatherRemote;
import com.rperazzo.weatherapp.presentation.WeatherContract;

import java.util.List;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    WeatherRemote mRemote;

    public WeatherRepositoryImpl(WeatherRemote remote) {
        mRemote = remote;
    }

    @Override
    public Single<List<City>> search(WeatherContract.Presenter presenter, String text, String units) {

        if (text != null && !text.isEmpty() && units != null && !units.isEmpty()) {

            return mRemote.find(text, units, presenter);
        }

        return null;
    }
}

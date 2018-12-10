package com.rperazzo.weatherapp.model.weather;

import com.rperazzo.weatherapp.model.weather.remote.WeatherRemote;
import java.util.List;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    WeatherRemote mRemote;

    public WeatherRepositoryImpl(WeatherRemote remote) {
        mRemote = remote;
    }

    @Override
    public Single<List<City>> search(String text, String units) {

        if (text != null && !text.isEmpty() && text.length() >= 3 && units != null && !units.isEmpty()) {
            return mRemote.find(text, units);
        }
        else
        {
            return Single.error (new IllegalArgumentException("Texto deve ter ao menos tres caracteres."));
        }
    }
}

package com.rperazzo.weatherapp.model.weather.remote;

import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.presentation.WeatherContract;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRemoteImpl implements WeatherRemote {

    WeatherService mWeatherService;

    public  WeatherRemoteImpl() {
        mWeatherService = WeatherManager.getService();
    }

    public Single<List<City>> find(String text, String units) {

        final Single<FindResult> findCall = mWeatherService.find(text, units, WeatherManager.API_KEY);
        Single<List<City>> cidades = findCall.map(new Function<FindResult, List<City>>() {
            @Override
            public List<City> apply(FindResult findResult) throws Exception {
                return findResult.list;
            }
        });
        return cidades;
    }

}

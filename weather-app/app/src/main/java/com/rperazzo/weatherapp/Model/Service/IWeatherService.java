package com.rperazzo.weatherapp.Model.Service;

import com.rperazzo.weatherapp.WeatherManager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherService {
    @GET("find")
    Call<WeatherManager.FindResult> find(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}

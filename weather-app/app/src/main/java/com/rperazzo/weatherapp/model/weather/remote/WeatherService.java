package com.rperazzo.weatherapp.model.weather.remote;

import retrofit2.http.GET;
import retrofit2.http.Query;
import io.reactivex.Single;

public interface WeatherService {
    @GET("find")
    Single<FindResult> find(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}

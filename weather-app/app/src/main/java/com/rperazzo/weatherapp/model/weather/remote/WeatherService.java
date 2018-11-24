package com.rperazzo.weatherapp.model.weather.remote;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("find")
    Single<FindResult> find(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}

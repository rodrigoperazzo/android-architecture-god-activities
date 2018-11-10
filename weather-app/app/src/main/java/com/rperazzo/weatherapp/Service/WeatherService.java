package com.rperazzo.weatherapp.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Thiago on 10/11/2018.
 */

public interface WeatherService {
    @GET("find")
    Call<WeatherManager.FindResult> find(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}

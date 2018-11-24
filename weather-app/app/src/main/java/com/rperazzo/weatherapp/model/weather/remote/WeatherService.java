package com.rperazzo.weatherapp.model.weather.remote;

import com.rperazzo.weatherapp.model.weather.WeatherRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
//    @GET("find")
//    Single<FindResult> find(
//            @Query("q") String cityName,
//            @Query("units") String units,
//            @Query("appid") String apiKey
//    );


    @GET("find")
    Single<FindResult> find(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("appid") String apiKey
    );


}
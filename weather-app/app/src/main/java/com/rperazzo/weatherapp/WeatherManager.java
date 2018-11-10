package com.rperazzo.weatherapp;
import java.util.List;
import com.rperazzo.weatherapp.Model.*;
import com.rperazzo.weatherapp.Model.Service.IWeatherService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherManager  {

    private static final String API_URL =
            "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY =
            "520d6b47a12735bee8f69c57737d145f";

    private static OkHttpClient mClient = new OkHttpClient();

    public static IWeatherService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();

        return retrofit.create(IWeatherService.class);
    }

    public class FindResult {
        public final List<City> list;

        public FindResult(List<City> list) {
            this.list = list;
        }
    }
}



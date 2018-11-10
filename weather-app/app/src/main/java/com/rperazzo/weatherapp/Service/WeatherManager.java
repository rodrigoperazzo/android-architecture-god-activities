package com.rperazzo.weatherapp.Service;

import com.rperazzo.weatherapp.Model.City;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherManager {

    private static final String API_URL =
            "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY =
            "520d6b47a12735bee8f69c57737d145f";

    private static OkHttpClient mClient = new OkHttpClient();

    public static WeatherService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();

        return retrofit.create(WeatherService.class);
    }

    public class FindResult {
        public final List<City> list;

        public FindResult(List<City> list) {
            this.list = list;
        }
    }


}



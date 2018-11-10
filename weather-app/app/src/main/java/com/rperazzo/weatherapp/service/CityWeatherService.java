package com.rperazzo.weatherapp.service;

import android.content.SharedPreferences;
import android.util.Log;

import com.rperazzo.weatherapp.WeatherManager;
import com.rperazzo.weatherapp.listener.OnSearchCity;
import com.rperazzo.weatherapp.model.City;
import com.rperazzo.weatherapp.provider.IWeatherProvider;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityWeatherService {

    private List<City> cities;
    private static OkHttpClient mClient = new OkHttpClient();
    private static final String API_URL =
            "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY =
            "520d6b47a12735bee8f69c57737d145f";



    public void find(String city, final OnSearchCity onSearchCity, String temperatureUnit){

        cities = new ArrayList<City>();

        IWeatherProvider wService = WeatherManager.getService();

        final Call<WeatherManager.FindResult> findCall = wService.find(city, temperatureUnit, WeatherManager.API_KEY);
        findCall.enqueue(new Callback<WeatherManager.FindResult>() {

            @Override
            public void onFailure(Call<WeatherManager.FindResult> call, Throwable t) {

            }

            @Override
            public void onResponse(Call<WeatherManager.FindResult> call, Response<WeatherManager.FindResult> response) {


                cities = response.body().list;
                onSearchCity.onSearchCity();
            }
        });


    }



    public static IWeatherProvider getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();

        return retrofit.create(IWeatherProvider.class);
    }

    public List<City> getCities(){

        return  cities;
    }
}

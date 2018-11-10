package com.rperazzo.weatherapp.listener;

import com.rperazzo.weatherapp.WeatherManager;
import com.rperazzo.weatherapp.model.City;
import com.rperazzo.weatherapp.provider.IWeatherProvider;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TempRepositoryRetrofit implements ITempRepository{

    private static OkHttpClient mClient = new OkHttpClient();
    private static final String API_URL =
            "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY =
            "520d6b47a12735bee8f69c57737d145f";


    @Override
    public void getCidades(String city, String unidade, final OnFindCitiesCallback callback) {

        IWeatherProvider wService = WeatherManager.getService();

        final Call<WeatherManager.FindResult> findCall = wService.find(city, unidade, WeatherManager.API_KEY);
        findCall.enqueue(new Callback<WeatherManager.FindResult>() {

            @Override
            public void onFailure(Call<WeatherManager.FindResult> call, Throwable t) {

            }

            @Override
            public void onResponse(Call<WeatherManager.FindResult> call, Response<WeatherManager.FindResult> response) {

                callback.onFindCitie(response.body().list);
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
}

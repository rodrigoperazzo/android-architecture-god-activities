package com.rperazzo.weatherapp;
import java.util.List;
import com.rperazzo.weatherapp.Model.*;
import com.rperazzo.weatherapp.Model.Service.IWeatherService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.rperazzo.weatherapp.View.*;

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

    public static void getResults(String search, String units, final ICallback callback){

        IWeatherService wService = WeatherManager.getService();

        final Call<FindResult> findCall = wService.find(search, units, API_KEY);
        findCall.enqueue(new Callback<FindResult>() {
            @Override
            public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                callback.onFinishLoading(response.body());
            }

            @Override
            public void onFailure(Call<FindResult> call, Throwable t) {
                callback.onFinishLoadingWithError();
            }
        });
    }

}



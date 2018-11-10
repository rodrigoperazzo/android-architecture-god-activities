package com.rperazzo.weatherapp;
import java.util.List;
import com.rperazzo.weatherapp.Model.City;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class WeatherManager {

    private static final String API_URL =
            "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY =
            "520d6b47a12735bee8f69c57737d145f";

    public interface WeatherService {
        @GET("find")
        Call<FindResult> find(
                @Query("q") String cityName,
                @Query("units") String units,
                @Query("appid") String apiKey
        );
    }

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



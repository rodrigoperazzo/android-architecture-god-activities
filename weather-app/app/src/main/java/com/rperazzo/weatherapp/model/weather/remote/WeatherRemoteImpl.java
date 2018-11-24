package com.rperazzo.weatherapp.model.weather.remote;

import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.presentation.WeatherContract;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRemoteImpl implements WeatherRemote {

    WeatherService mWeatherService;

    public  WeatherRemoteImpl() {
        mWeatherService = WeatherManager.getService();
    }

    public Single<List<City>> find(String text, String units, final WeatherContract.Presenter presenter) {


        Single<FindResult> find = mWeatherService.find(text, units, WeatherManager.API_KEY);

        return find.map(new Function<FindResult, List<City>>() {

            @Override
            public List<City> apply(FindResult findResult) throws Exception {

                return findResult.list;
            }
        });


//        final Call<FindResult> findCall = mWeatherService.find(text, units, WeatherManager.API_KEY);
//        findCall.enqueue(new Callback<FindResult>() {
//            @Override
//            public void onResponse(Call<FindResult> call, Response<FindResult> response) {
//                if (response != null) {
//                    FindResult result = response.body();
//                    if (result != null) {
//                        presenter.onFinishSearching(result.list);
//                    } else {
//                        presenter.onFinishSearching(null);
//                    }
//                } else {
//                    presenter.onFinishSearching(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FindResult> call, Throwable t) {
//                presenter.onFinishSearchingWithError("error");
//            }
//        });
    }

}

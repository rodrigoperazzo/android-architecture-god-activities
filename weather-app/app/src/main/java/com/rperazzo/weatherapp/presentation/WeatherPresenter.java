package com.rperazzo.weatherapp.presentation;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.rperazzo.weatherapp.model.settings.SettingsRepository;
import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.model.weather.WeatherRepository;
import com.rperazzo.weatherapp.model.weather.remote.FindResult;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter implements WeatherContract.Presenter {

    WeatherRepository mWeatherRepository;
    SettingsRepository mSettingsRepository;
    WeatherContract.View mView;

    public WeatherPresenter(WeatherRepository weatherRepo, SettingsRepository settingsRepo) {
        mWeatherRepository = weatherRepo;
        mSettingsRepository = settingsRepo;
    }

    @Override
    public void onAttachView(WeatherContract.View view) {
        mView = view;
    }

    @Override
    public void onDettachView() {
        mView = null;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSearchClick(String searchText) {
        if (mView == null) {
            return;
        }

        if (searchText == null || searchText.isEmpty()) {
            return;
        }

        mView.onStartLoading();
        final String units = mSettingsRepository.getTemperatureUnit();

        mWeatherRepository.search(searchText, units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FindResult>(){

                    @Override
                    public void onSuccess(FindResult findResult) {
                        if(findResult != null & findResult.list.size() > 0)
                            mView.onFinishLoading(findResult.list, units);
                        else
                            mView.onFinishLoadingWithError("No results!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFinishLoadingWithError(e.getMessage());
                    }
                });
    }

    @Override
    public void onUnitClick(String unitClicked, String currentText) {
        String currentUnits = mSettingsRepository.getTemperatureUnit();
        if (!currentUnits.equals(unitClicked)) {
            mSettingsRepository.setTemperatureUnit(unitClicked);
            onSearchClick(currentText);
        }
    }

    @Override
    public void onFinishSearching(List<City> list) {
        if (mView == null) {
            return;
        }
        if (list != null && list.size() > 0) {
            mView.onFinishLoading(list, mSettingsRepository.getTemperatureUnit());
        } else {
            mView.onFinishLoadingWithError("No results.");
        }
    }

    @Override
    public void onFinishSearchingWithError(String error) {
        mView.onFinishLoadingWithError(error);
    }
}

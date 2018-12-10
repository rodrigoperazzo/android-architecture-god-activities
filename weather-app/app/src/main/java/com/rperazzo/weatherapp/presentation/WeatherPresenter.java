package com.rperazzo.weatherapp.presentation;

import com.rperazzo.weatherapp.model.settings.SettingsRepository;
import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.model.weather.WeatherRepository;

import java.util.List;

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

    @Override
    public void onSearchClick(String searchText) {
        if (mView == null) {
            return;
        }

        if (searchText == null || searchText.isEmpty()) {
            return;
        }

        mView.onStartLoading();
        String units = mSettingsRepository.getTemperatureUnit();
        mWeatherRepository.search(searchText, units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<City>>(){
                    @Override
                    public void onSuccess(List<City> result) {
                        // update UI
                        onFinishSearching(result);
                    }
                    @Override
                    public void onError(Throwable e) {
                        // show error
                        onFinishSearchingWithError(e.getMessage());
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

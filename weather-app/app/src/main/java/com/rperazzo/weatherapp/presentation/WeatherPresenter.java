package com.rperazzo.weatherapp.presentation;

import android.annotation.SuppressLint;

import com.rperazzo.weatherapp.model.settings.SettingsRepository;
import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.model.weather.WeatherRepository;
import com.rperazzo.weatherapp.model.weather.remote.FindResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class WeatherPresenter implements WeatherContract.Presenter {

    WeatherRepository mWeatherRepository;
    SettingsRepository mSettingsRepository;

    private PublishSubject<Boolean> loadingSubject = PublishSubject.create();
    private PublishSubject<List<City>> listSubject = PublishSubject.create();
    private PublishSubject<String> unitsubject = PublishSubject.create();

    public WeatherPresenter(WeatherRepository weatherRepo, SettingsRepository settingsRepo) {
        mWeatherRepository = weatherRepo;
        mSettingsRepository = settingsRepo;
    }

    @Override
    public Observable<Boolean> getLoadingObservable() {
        return loadingSubject;
    }

    @Override
    public Observable<List<City>> getListObservable() {
        return listSubject;
    }

    @Override
    public Observable<String> getUnitsObservable() {
        return unitsubject;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSearchClick(String searchText) {
        loadingSubject.onNext(true);
        final String units = mSettingsRepository.getTemperatureUnit();

        mWeatherRepository.search(searchText, units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FindResult>(){

                    @Override
                    public void onSuccess(FindResult findResult) {
                        if(findResult != null & findResult.list.size() > 0) {
                            loadingSubject.onNext(false);
                            listSubject.onNext((findResult.list));
                            unitsubject.onNext(units);
                        }
                        else {
                            listSubject.onError(new Exception("No results!"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listSubject.onError(e);
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

}

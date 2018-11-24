package com.rperazzo.weatherapp.presentation;

import com.rperazzo.weatherapp.model.settings.SettingsRepository;
import com.rperazzo.weatherapp.model.weather.City;
import com.rperazzo.weatherapp.model.weather.WeatherRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class ViewModel implements WeatherContract.Presenter {

    WeatherRepository mWeatherRepository;
    SettingsRepository mSettingsRepository;
    WeatherContract.View mView;

    private BehaviorSubject<Boolean> loadingSubject = BehaviorSubject.create();
    private BehaviorSubject<String> tempSubject = BehaviorSubject.create();
    private PublishSubject<List<City>> citySubject = PublishSubject.create();


    public ViewModel(WeatherRepository weatherRepo, SettingsRepository settingsRepo) {
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

        loadingSubject.onNext(true);


        String units = mSettingsRepository.getTemperatureUnit();
        mWeatherRepository.search(this, searchText, units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<City>>() {
                    @Override
                    public void onSuccess(List<City> cities) {

                        onFinishSearching(cities);
                        //getCitySubject().onNext(cities);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onUnitClick(String unitClicked, String currentText) {
        String currentUnits = mSettingsRepository.getTemperatureUnit();
        if (!currentUnits.equals(unitClicked)) {
            mSettingsRepository.setTemperatureUnit(unitClicked);


            onSearchClick(currentText);
            tempSubject.onNext(unitClicked);
        }
    }

    @Override
    public void onFinishSearching(List<City> list) {
        if (mView == null) {
            return;
        }
        if (list != null && list.size() > 0) {
//            mView.onFinishLoading(list, mSettingsRepository.getTemperatureUnit());
            // loading false
            citySubject.onNext(list);
        } else {
            mView.onFinishLoadingWithError("No results.");
        }
    }

    @Override
    public void onFinishSearchingWithError(String error) {
        mView.onFinishLoadingWithError(error);
    }

    public Observable<Boolean> getLoadingObservable() {
        return loadingSubject;
    }

    public Observable<List<City>> getCitySubject() {
        return citySubject;
    }


    public Observable<String> getTempSubject() {
        return tempSubject;
    }
}

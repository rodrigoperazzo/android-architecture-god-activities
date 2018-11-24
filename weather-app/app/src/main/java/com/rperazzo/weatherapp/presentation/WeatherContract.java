package com.rperazzo.weatherapp.presentation;

import com.rperazzo.weatherapp.model.weather.City;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherContract {

    interface Presenter {
        void onSearchClick(String searchText);
        void onUnitClick(String unitClicked, String currentText);
        Observable<Boolean> getLoadingObservable();
        Observable<List<City>> getListObservable();
        Observable<String> getUnitsObservable();
    }

    interface View {
    }
}

package com.rperazzo.weatherapp.presentation;

import com.rperazzo.weatherapp.model.weather.City;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherContract {

    interface Presenter {
        //void onAttachView(View view);
        //void onDettachView();
        void onSearchClick(String searchText);
        void onUnitClick(String unitClicked, String currentText);
        void onFinishSearching(List<City> list);
        void onFinishSearchingWithError(String error);
        Observable<List<City>> getCityListObservable();
        Observable<Boolean> getLoadingObservable();
        Observable<String> getErrorObservable();
        Observable<String> getTemperatureUnitObservable();

    }

    interface View {
        void onStartLoading();
        void onFinishLoading(List<City> list);
        void onFinishLoadingWithError(String error);
        void onStopLoading();
        void onChangeUnit( String units);
    }
}

package com.rperazzo.weatherapp.Presenter;

import com.rperazzo.weatherapp.View.IView;

public interface IPresenter {
    void updateUnitIfNecessary(String newUnits, String search);
    void searchByName(String search);
    String getTemperatureUnit();
}

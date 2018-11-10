package com.rperazzo.weatherapp.listener;

import com.rperazzo.weatherapp.model.City;

import java.util.List;

public interface ITempRepository {


    void getCidades(String city, String unidade, OnFindCitiesCallback callback);

    public interface OnFindCitiesCallback {

        void onFindCitie(List<City> cities);
    }
}

package com.rperazzo.weatherapp.service;

import com.rperazzo.weatherapp.listener.ITempRepository;
import com.rperazzo.weatherapp.listener.OnSearchCity;
import com.rperazzo.weatherapp.model.City;

import java.util.List;

public class CityWeatherService {

    private List<City> cities;

    private ITempRepository iTempRepository;


    public CityWeatherService(ITempRepository iTempRepository){

        this.iTempRepository = iTempRepository;
    }


    public void find(String city, final OnSearchCity onSearchCity, String temperatureUnit){

        iTempRepository.getCidades(city, temperatureUnit, new ITempRepository.OnFindCitiesCallback() {
            @Override
            public void onFindCitie(List<City> cities) {

                CityWeatherService.this.cities = cities;
                onSearchCity.onSearchCity();
            }
        });
    }

    public List<City> getCities(){

        return  cities;
    }
}

package com.rperazzo.weatherapp.service;

import com.rperazzo.weatherapp.listener.ITempRepository;
import com.rperazzo.weatherapp.listener.OnSearchCity;
import com.rperazzo.weatherapp.model.City;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CityWeatherServiceTest {

/*
   ArrayList city = new ArrayList<City>();

    public void findCityTest(){
        City recife = new City();

        ITempRepository repository = new ITempRepository() {
            @Override
            public void getCidades(String city, String unidade, OnFindCitiesCallback callback) {

                city = "Recife";
                unidade = "c";
                callback = new OnFindCitiesCallback() {
                    @Override
                    public void onFindCitie(List<City> cities) {

                        cities = CityWeatherServiceTest.this.city;
                    }
                };

            }
        };

        CityWeatherService service = new CityWeatherService(repository);

        boolean ExpResult = true;
        boolean result = service.find("Recife", new OnSearchCity() {
            @Override
            public void onSearchCity() {

            },
        }, "c");

       // return equals(ExpResult, result);

    }
*/
}
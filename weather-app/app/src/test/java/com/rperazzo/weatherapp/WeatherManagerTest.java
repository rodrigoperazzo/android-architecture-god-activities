package com.rperazzo.weatherapp;

import com.rperazzo.weatherapp.Model.City;
import com.rperazzo.weatherapp.View.ICallback;
import com.rperazzo.weatherapp.Model.IWeatherManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.rperazzo.weatherapp.WeatherManager.*;

public class WeatherManagerTest {

    IWeatherManager weatherManager;

    ICallback callback;

    @Before
    public void init(){
         MockitoAnnotations.initMocks(callback);

    }
    @Test
    public void testFindCityExpectedOlinda() {

//        WeatherManager weatherManager = new WeatherManager();
//
//        City city = new City();
//        city.name = "Olinda";
//        List<City> list = new ArrayList<City>();
//        list.add(city);
//
//        WeatherManager.FindResult findResult = new WeatherManager.FindResult(list);
//
//        when(callback.onFinishLoading(findResult));
//        when(weatherManager.getResults("olinda","C",callback));
//
//        //action
//        weatherManager.getResults("olinda","C",callback);
//
//
//        Assert.assertEquals("Olinda", return.);

    }
}
package com.rperazzo.weatherapp.service;

import com.rperazzo.weatherapp.MainActivity;
import com.rperazzo.weatherapp.controller.WeatherController;
import com.rperazzo.weatherapp.listener.OnSearchCity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class CityWeatherServiceTest {

    WeatherController controller;
    @Mock
    MainActivity main;
    @Mock
    CityWeatherService service;

    @Captor
    private ArgumentCaptor<OnSearchCity> argumentCaptor;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        controller = new WeatherController(main, service);
    }

    @Test
    public void testFindCity(){
        String city = "recife";
        controller.searchByName(city,"F");

        verify(service).find(eq(city), argumentCaptor.capture(), eq("F"));
        argumentCaptor.getValue().onSearchCity();

        verify(main).update();
    }


}
package com.rperazzo.weatherapp;

import com.rperazzo.weatherapp.Model.City;
import com.rperazzo.weatherapp.View.IView;
import com.rperazzo.weatherapp.Model.IWeatherManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;

public class WeatherManagerTest {

    IWeatherManager weatherManager;

    @Test
    public void testFindCityExpectedOlinda() {

        IWeatherManager weatherManager = Mockito.spy(IWeatherManager.class);
        IView callback = Mockito.spy(IView.class);
        weatherManager.getResults("Olinda","metric",callback);

        Mockito.verify(callback).onFinishLoading((WeatherManager.FindResult) any());

    }
}
package com.rperazzo.weatherapp;

import com.rperazzo.weatherapp.Model.City;
import com.rperazzo.weatherapp.View.ICallback;
import com.rperazzo.weatherapp.Model.IWeatherManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherManagerTest {

    @Test
    public void testFindCityExpectedOlinda() {

        IWeatherManager weatherManager = Mockito.spy(IWeatherManager.class);
        ICallback callback = Mockito.spy(ICallback.class);
       

        weatherManager.getResults("Olinda","metric",callback);

        Mockito.verify(callback).onFinishLoading((WeatherManager.FindResult) any());


    }
}
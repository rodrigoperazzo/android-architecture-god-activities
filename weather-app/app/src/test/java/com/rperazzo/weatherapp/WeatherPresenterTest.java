package com.rperazzo.weatherapp;

import com.rperazzo.weatherapp.listener.IView;
import com.rperazzo.weatherapp.service.CityWeatherService;

import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class WeatherPresenterTest {
    @Test
    public void mostrarSemInternetTest(){
        IView view = mock(IView.class);
        CityWeatherService service = mock(CityWeatherService.class);
        IsDeviceChecking check = mock(IsDeviceChecking.class);

        
        when(check.isDeviceConnected()).thenReturn(false);

        WeatherPresenter presenter = new WeatherPresenter(view, service, check);
        presenter.searchByName("Recife", "c");

        verify(view).mostrarSemInternet();
    }

}
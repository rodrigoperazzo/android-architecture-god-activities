package com.rperazzo.weatherapp.Model;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class CityTest {

    @Test
    public void getTitle() {
        City city = new City();
        city.name = "Recife";

        City.Sys sys = city.new Sys();
        sys.country = "brasil";

        city.sys = sys;

        assertEquals("Recife, BRASIL", city.getTitle());
    }

    @Test
    public void getPressure() {
        City city = new City();

        City.Main main = city.new Main();
        main.pressure = 12;

        city.main = main;

        assertEquals("12 hpa", city.getPressure());
    }

    @Test
    public void getWind() {
        City city = new City();

        City.Wind wind = city.new Wind();
        wind.speed = 12;

        city.wind = wind;

        assertEquals("wind 12", city.getWind());
    }

    @Test
    public void getClouds() {
        City city = new City();

        City.Clouds clouds = city.new Clouds();
        clouds.all = 12;

        city.clouds = clouds;

        assertEquals("clouds 12%", city.getClouds());
    }

    @Test
    public void getTemperature() {
        City city = new City();

        City.Main main = city.new Main();
        main.temp = 12;

        city.main = main;

        assertEquals("12", city.getTemperature());
    }

    @Test
    public void getDescription() {
        City city = new City();

        City.Weather weather = city.new Weather();
        weather.description = "weather";

        List<City.Weather> weatherList = new LinkedList<>();
        weatherList.add(weather);

        city.weather = weatherList;

        assertEquals("weather", city.getDescription());
    }

}
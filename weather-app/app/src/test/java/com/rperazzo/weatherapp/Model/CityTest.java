package com.rperazzo.weatherapp.Model;

import org.junit.Test;

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
}
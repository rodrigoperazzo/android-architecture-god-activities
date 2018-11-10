package com.rperazzo.weatherapp.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {

    City city;

    String name = "Recife";
    String country = "Brazil";

    @Before
    public void setUp() throws Exception {
        city = new City();
        city.name = name;
        Sys sys = new Sys();
        sys.country = country;
        city.sys = sys;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public  void testTemperature(){

        string cidade = "olinda";




    }

    @Test
    public void testCityName(){
        assertNotNull(city.name);
        assertEquals(name, city.name);
    }

    @Test
    public void testCitySys(){
        assertEquals(country, city.sys.country);
    }

    @Test
    public void testGetTitle(){
        String expected = "Recife, BRAZIL";
        assertEquals(expected, city.getTitle());
    }

}
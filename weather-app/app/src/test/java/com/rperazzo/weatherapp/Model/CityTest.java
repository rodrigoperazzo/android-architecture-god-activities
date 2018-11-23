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

    @Test
    public void testGetTitle(){
        String expected = "Recife, BRAZIL";



        assertEquals(expected, city.getTitle());
    }

}
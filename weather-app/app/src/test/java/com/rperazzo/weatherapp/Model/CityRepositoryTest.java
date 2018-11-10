package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.Util.IConnectivityUtil;

import org.junit.Test;

import static org.junit.Assert.*;

public class CityRepositoryTest {

    @Test
    public void searchByName() {

        Presenter presenter = new Presenter();
        CityRepository repo = new CityRepository();
        String text = "";

        assertEquals(false, repo.searchByName(text, presenter, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return true;
            }
        }));
    }

    @Test
    public void searchByNameWithText() {

        Presenter presenter = new Presenter();
        CityRepository repo = new CityRepository();
        String text = "recife";

        assertEquals(true, repo.searchByName(text, presenter, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return true;
            }
        }));

    }

    @Test
    public void searchByNameNotConnect() {

        Presenter presenter = new Presenter();
        CityRepository repo = new CityRepository();
        String text = "recife";

        assertEquals(false, repo.searchByName(text, presenter, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return false;
            }
        }));

    }
}
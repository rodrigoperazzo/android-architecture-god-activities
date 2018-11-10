package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.MainActivity;
import com.rperazzo.weatherapp.Util.ConnectivityUtil;
import com.rperazzo.weatherapp.Util.IConnectivityUtil;
import com.rperazzo.weatherapp.View.Interface.ISearch;

import org.junit.Test;

import static org.junit.Assert.*;

public class CityRepositoryTest {

    @Test
    public void searchByName() {

        Search main = new Search();
        CityRepository repo = new CityRepository();
        String text = "";

        assertEquals(false, repo.searchByName(text, main, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return true;
            }
        }));

    }

    @Test
    public void searchByNameWithText() {

        Search main = new Search();
        CityRepository repo = new CityRepository();
        String text = "recife";

        assertEquals(true, repo.searchByName(text, main, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return true;
            }
        }));

    }

    @Test
    public void searchByNameNotConnect() {

        Search main = new Search();
        CityRepository repo = new CityRepository();
        String text = "recife";

        assertEquals(false, repo.searchByName(text, main, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return false;
            }
        }));

    }
}
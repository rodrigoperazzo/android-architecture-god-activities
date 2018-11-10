package com.rperazzo.weatherapp.Presenter;

import com.rperazzo.weatherapp.Model.CityRepository;
import com.rperazzo.weatherapp.Model.Presenter;
import com.rperazzo.weatherapp.Model.Search;
import com.rperazzo.weatherapp.Util.IConnectivityUtil;
import com.rperazzo.weatherapp.View.Interface.ISearch;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thiago on 10/11/2018.
 */
public class WeatherPresenterTest {

    @Test
    public void searchByName() {

        Search search = new Search();
        IWeatherPresenter presenter = new WeatherPresenter(search);
        String text = "";

        presenter.searchByName(text, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return true;
            }
        });

        assertEquals("onFinishLoadingWithError", search.resultSearch);
    }

    @Test
    public void searchByNameWithText() {

        Search search = new Search();
        IWeatherPresenter presenter = new WeatherPresenter(search);
        String text = "recife";

        presenter.searchByName(text, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return true;
            }
        });

        assertEquals("onFinishLoading", search.resultSearch);

    }

    @Test
    public void searchByNameNotConnect() {

        Search search = new Search();
        IWeatherPresenter presenter = new WeatherPresenter(search);
        String text = "recife";

        presenter.searchByName(text, "C", "en", new IConnectivityUtil() {
            @Override
            public boolean isDeviceConnected() {
                return false;
            }
        });

        assertEquals("onFinishLoadingWithError", search.resultSearch);

    }

}
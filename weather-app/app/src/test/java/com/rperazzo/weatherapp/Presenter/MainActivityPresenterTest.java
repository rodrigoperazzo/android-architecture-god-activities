package com.rperazzo.weatherapp.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.rperazzo.weatherapp.View.IView;
import com.rperazzo.weatherapp.WeatherManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

public class MainActivityPresenterTest implements  IView  {

    MainActivityPresenter _presenter;
    Context _context;
    @Before
    public void before() throws Exception {
        _context = Mockito.mock(Context.class);

    }


    @Test
    public void searchByNameOlindaExpectedRecordOlinda() {
        _presenter = new MainActivityPresenter(this);
        _presenter.searchByName("Olinda");


    }

    @Override
    public void onFinishLoading(WeatherManager.FindResult result) {
    }

    @Override
    public void onFinishLoadingWithError() {

    }

    @Override
    public Context getContext() {
        return _context;
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}
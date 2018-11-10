package com.rperazzo.weatherapp.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.rperazzo.weatherapp.View.IView;
import com.rperazzo.weatherapp.WeatherManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

public class MainActivityPresenterTest   {

    IView _view;
    MainActivityPresenter _presenter;
    Context _context;
    @Before
    public void before() throws Exception {
        _view = Mockito.mock(IView.class);
        _context = Mockito.mock(Context.class);
    }

    @Test
    public void searchByNameOlindaExpectedRecordOlinda() {
        _presenter = new MainActivityPresenter(_view);
        _presenter.searchByName("Olinda");
        Mockito.verify(_view,Mockito.never()).onFinishLoadingWithError();

    }
}